package cn.coco.busi;

import java.util.List;

/**
 * 查询逻辑
 */
public interface QueryBusi {
    /**
     * 查询数据
     * @param count 一次查询的数量
     * @return
     */
    List queryList(int count);

    /**
     * 修改数据状态
     * @param data 待修改的数据
     * @param status 要修改的状态
     * @return
     */
    int modifyListStatus (List data, String status);

}
