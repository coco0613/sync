package cn.coco.main;

import cn.coco.busi.DealBusi;
import cn.coco.busi.QueryBusi;
import cn.coco.busi.impl.DealBusiImpl;
import cn.coco.busi.impl.QueryBusiImpl;
import cn.coco.threads.Consumer;
import cn.coco.threads.Producer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动类
 */
public class Main {

    public static void main(String[] args) {
        QueryBusi queryBusi = new QueryBusiImpl();
        DealBusi dealBusi = new DealBusiImpl();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));
        LinkedBlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>(10);
        for (int i = 0; i < 10; i++) {
            try {
                runnables.put(new Consumer(dealBusi, runnables));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Producer producer = new Producer(queryBusi, runnables, executor);
        new Thread(producer).start();
    }
}
