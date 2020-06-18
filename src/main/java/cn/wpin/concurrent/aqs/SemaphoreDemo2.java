package cn.wpin.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量高级用法demo
 * 原理：如果构造参数传入的是N，调用方法acquire方法传入的是M，则需要在M+N个线程调用release方法
 * 1.主线程执行acquire方法会被阻塞，然后两个子线程调用release方法，信号量变成2了，然后主线程就会在获取
 * 两个信号量后返回（返回后当前信号量为0（这个数字就是构造方法中的参数））
 *
 * @author wangpin
 */
public class SemaphoreDemo2 {

    private static Semaphore semaphore = new Semaphore(0);

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws InterruptedException {

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-task A --over");
            semaphore.release();
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-task A --over");
            semaphore.release();
        });

        semaphore.acquire(2);
        System.out.println("task is over");

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-task B --over");
            semaphore.release();
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "-task B --over");
            semaphore.release();
        });
        semaphore.acquire(2);

        System.out.println(" taskB is over");

        executorService.shutdown();

    }

    //输出结果：
    //Thread[pool-1-thread-1,5,main]-task A --over
    //Thread[pool-1-thread-2,5,main]-task A --over
    //task is over
    //Thread[pool-1-thread-3,5,main]-task B --over
    //Thread[pool-1-thread-4,5,main]-task B --over
    // taskB is over
}
