package cn.coco.middle.mapper;

import cn.coco.middle.model.User;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectByPrimaryKey(User record);

    int deleteByPrimaryKey(User record);
}