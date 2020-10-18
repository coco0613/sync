package cn.coco.threads;

import cn.coco.busi.QueryBusi;
import cn.coco.consts.DataStatus;
import org.apache.log4j.spi.LoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 生产者类
 */
public class Producer implements Runnable {

    private static Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private QueryBusi queryBusi;
    //存放消费者队列
    private LinkedBlockingQueue<Runnable> consumers;
    private ThreadPoolExecutor executor;

    public Producer(QueryBusi queryBusi, LinkedBlockingQueue<Runnable> consumers, ThreadPoolExecutor executor) {
        this.queryBusi = queryBusi;
        this.consumers = consumers;
        this.executor = executor;
    }

    public Producer(QueryBusi queryBusi) {
        this.queryBusi = queryBusi;
    }

    public void run() {
        while (true){
            List list = queryBusi.queryList(10);
            try {
                System.err.println("===============>" + list);
                if (list != null && list.size() > 0){
                    queryBusi.modifyListStatus(list, DataStatus.DEALING);
                    //取出消费者
                    Consumer consumer = (Consumer) consumers.take();
                    consumer.setData(list);
                    executor.execute(consumer);
                }else {
                    try {
                        Thread.sleep(5000L);
                        System.out.println("已休眠5秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                LOGGER.error("生产者发生异常------->", e);
                queryBusi.modifyListStatus(list, DataStatus.ERROR);
            }
        }

    }
}
