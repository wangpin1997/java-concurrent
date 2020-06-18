package cn.wpin.concurrent.queue;

import cn.wpin.concurrent.aqs.Consumer;
import cn.wpin.concurrent.aqs.Producer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 基于链表的 阻塞队列 LinkedBlockingQueue
 *
 * @author wangpin
 */
public class LinkedBlockingQueueDemo {

    static ExecutorService service;

    public static void main(String[] args) throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

        //定义三个生产者，一个消费者
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        // 借助Executors
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("p1").build();
        service = new ThreadPoolExecutor(3, 5, 2L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), factory);
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        Thread.sleep(5 * 1000);
        //生产者停止生产
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }

}
