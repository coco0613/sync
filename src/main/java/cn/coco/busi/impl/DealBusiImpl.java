package cn.coco.busi.impl;

import cn.coco.busi.DealBusi;
import cn.coco.consts.DataStatus;
import cn.coco.middle.model.User;
import cn.coco.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DealBusiImpl implements DealBusi {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealBusiImpl.class);

    @Override
    public void Deal(List data) {
        //将数据转换成业务库的实体
        List<cn.coco.our.model.User> userList = adepter(data);
        //处理数据并入库
        userList.forEach(user -> {
            user.setName(user.getName() + "TEST");
            SqlSession ourSession = SqlSessionUtil.getSqlSession("our");
            try {
                ourSession.insert("cn.coco.our.mapper.UserMapper.insertSelective", user);
                ourSession.commit();
                //修改中间表的状态
                modifyMiddle(user.getId(), DataStatus.FINISH);
            }catch (Exception e){
                LOGGER.error("处理数据发生异常,插入失败----->", e);
                //发生异常，修改中间表状态为10E
                modifyMiddle(user.getId(), DataStatus.ERROR);
            }finally {
                ourSession.close();
            }
        });
    }

    /**
     * 根据user id修改状态
     * @param id
     * @param status
     */
    private void modifyMiddle(int id, String status){
        User user = new User();
        user.setId(id);
        user.setAdd_status(status);
        user.setDeal_time(new Date());

        SqlSession middleSession = middleSession = SqlSessionUtil.getSqlSession("middle");;
        try{
            middleSession.update("cn.coco.middle.mapper.UserMapper.updateStatusById", user);
            middleSession.commit();
        }catch (Exception e){
            LOGGER.error("修改失败------>", e);
        }finally {
            middleSession.close();
        }

    }

    /**
     * 数据适配，将中间表的数据适配到业务库
     * @param userList
     * @return
     */
    private List<cn.coco.our.model.User> adepter(List<User> userList){
        List<cn.coco.our.model.User> result = new ArrayList<>();
        userList.forEach(user -> {
            cn.coco.our.model.User userOur = new cn.coco.our.model.User();
            userOur.setId(user.getId() );
            userOur.setName(user.getName());
            userOur.setSex(user.getSex());
            userOur.setBirth(user.getBirth());
            userOur.setAdd_time(new Date());
            userOur.setDepartment(user.getDepartment());
            result.add(userOur);
        });
        return result;
    }
}
