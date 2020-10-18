package cn.coco.test;

import cn.coco.busi.QueryBusi;
import cn.coco.busi.impl.QueryBusiImpl;
import org.junit.Test;

import java.util.List;

public class UserTest {

    @Test
    public void testList(){
        QueryBusi queryBusi = new QueryBusiImpl();
        List list = queryBusi.queryList(2);
        System.out.println(list);
    }
    @Test
    public void testUpdate(){
        QueryBusi queryBusi = new QueryBusiImpl();
        List list = queryBusi.queryList(2);
        queryBusi.modifyListStatus(list, "10D");
    }

}
