package cn.coco.busi.impl;

import cn.coco.busi.QueryBusi;
import cn.coco.middle.model.User;
import cn.coco.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QueryBusiImpl implements QueryBusi {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBusiImpl.class);

    public List queryList(int count) {

        SqlSession middleSession = SqlSessionUtil.getSqlSession("middle");
        List<Object> objects = null;
        try {
            objects = middleSession.selectList("cn.coco.middle.mapper.UserMapper.queryList", count);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("查询失败发生异常---------->", e);
        }finally {
            middleSession.close();
        }
        return objects;
    }

    public int modifyListStatus(List data, String status) {

        List<User> userList = data;
        userList.forEach(user->{
            user.setAdd_status(status);
            SqlSession middleSession = SqlSessionUtil.getSqlSession("middle");
            try {
                middleSession.update("cn.coco.middle.mapper.UserMapper.updateByPrimaryKey", user);
                middleSession.commit();
            }catch (Exception e){
                LOGGER.error("修改状态失败----------->", e);
                e.printStackTrace();
            }finally {
                middleSession.close();
            }
        });
        return 0;
    }

}
