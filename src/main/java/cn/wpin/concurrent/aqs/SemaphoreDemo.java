package cn.wpin.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量用法demo
 *
 * @author wangpin
 */
public class SemaphoreDemo {

    private static Semaphore semaphore = new Semaphore(0);

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-A --over");
            semaphore.release();
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-B --over");
            semaphore.release();
        });

        semaphore.acquire(2);
        System.out.println(" all child thread over");

        executorService.shutdown();

    }
}
