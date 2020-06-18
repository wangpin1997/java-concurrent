package cn.wpin.concurrent.queue;

import cn.wpin.concurrent.aqs.Consumer;
import cn.wpin.concurrent.aqs.Producer;

import java.util.concurrent.*;

/**
 * 基于数组的阻塞队列测试
 * <p>
 * 经典生产者-消费者模式
 *
 * @author wangpin
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        // 借助Executors
        ThreadFactory factory1 = Executors.defaultThreadFactory();
        ExecutorService service = new ThreadPoolExecutor(3, 5, 2L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), factory1);
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        Thread.sleep(5 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }

}
