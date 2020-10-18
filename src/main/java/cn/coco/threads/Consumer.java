package cn.coco.threads;

import cn.coco.busi.DealBusi;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消费者类
 */
public class Consumer implements Runnable {

    private List data;
    private DealBusi dealBusi;
    private LinkedBlockingQueue<Runnable> consumers;

    public Consumer(DealBusi dealBusi, LinkedBlockingQueue<Runnable> consumers) {
        this.dealBusi = dealBusi;
        this.consumers = consumers;
    }
    public void run() {
        try {
            dealBusi.Deal(data);
        } finally {
            try {
                consumers.put(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(List data) {
        this.data = data;
    }
}
