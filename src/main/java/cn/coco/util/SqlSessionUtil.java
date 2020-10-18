package cn.coco.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionUtil {

    //实例化工厂
    private static SqlSessionFactory middleSqlSessionFactory;
    private static SqlSessionFactory ourSqlSessionFactory;
    private static final String OUR = "our";
    private static final String MIDDLE = "middle";

    //读取配置文件
    private static final String MIDDLE_CONFIG = "mybatis-config-middle.xml";
    private static final String OUR_CONFIG = "mybatis-config-sql_test.xml";
    //初始化
    static {
        Reader middleResourceAsReader = null;
        Reader ourResourceAsReader = null;
        try {
            middleResourceAsReader = Resources.getResourceAsReader(MIDDLE_CONFIG);
            ourResourceAsReader = Resources.getResourceAsReader(OUR_CONFIG);
            middleSqlSessionFactory = new SqlSessionFactoryBuilder().build(middleResourceAsReader);
            middleSqlSessionFactory = new SqlSessionFactoryBuilder().build(ourResourceAsReader);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                middleResourceAsReader.close();
                ourResourceAsReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取SQLSession数据源
     * @param data
     * @return
     */
    public static SqlSession getSqlSession(String data){
        if (data.equals(MIDDLE)) {
            return middleSqlSessionFactory.openSession();
        }else if (data.equals(OUR)){
            return ourSqlSessionFactory.openSession();
        }
            return null;
    }
}
