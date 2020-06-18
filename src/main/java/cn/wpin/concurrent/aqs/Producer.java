package cn.wpin.concurrent.aqs;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 * @author wangpin
 */
public class Producer implements Runnable {

    private volatile boolean isRunning=true;

    private BlockingQueue queue;

    private static AtomicInteger count=new AtomicInteger();

    private static final int DEFAULT_RANGE_FOR_SLEEP=1000;

    private CountDownLatch countDownLatch;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public Producer(BlockingQueue queue,CountDownLatch countDownLatch) {
        this.queue = queue;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run() {
        String data;
        ThreadLocalRandom r = ThreadLocalRandom.current();
        if (countDownLatch!=null){
            countDownLatch.countDown();
        }
        System.out.println("启动生产者线程！");
        try {
            while (isRunning) {
                System.out.println("正在生产数据...");
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                data = "data:" + count.incrementAndGet();
                System.out.println(Thread.currentThread().getName()+"将数据：" + data + "放入队列...");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败：" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出生产者线程！");
        }
    }

    public void stop() {
        isRunning = false;
    }
}
