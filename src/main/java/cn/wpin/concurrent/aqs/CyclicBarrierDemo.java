package cn.wpin.concurrent.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 回环屏障demo
 *
 * 原理：初始化一个线程状态state值，和一个当线程state为0时需要执行的任务。
 * 然后执行两个子任务，各自调用一次await方法，阻塞当前线程，也就是线程状态值state-1-1为0的时候
 * 线程不再阻塞，调用第二个参数的线程执行任务，执行完毕后，两个子线程也就释放了
 * 如果没有第二个参数，则直接输出两个子线程await方法后面的即可
 *
 * @author wangpin
 */
public class CyclicBarrierDemo {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,
            () -> System.out.println(Thread.currentThread() + "--- task merge result"));

    private static ExecutorService executorService= Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        //线程A提交到线程池
        executorService.submit(()->{
            try {
                System.out.println(Thread.currentThread()+ " task1-1");
                System.out.println(Thread.currentThread()+ " enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+ " enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        //线程B提交到线程池
        executorService.submit(()->{
            try {
                System.out.println(Thread.currentThread()+ " task1-2");
                System.out.println(Thread.currentThread()+ " enter in barrier");
                cyclicBarrier.await();

                System.out.println(Thread.currentThread()+ " enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
    //输出结果
    //Thread[pool-1-thread-1,5,main] task1-1
    //Thread[pool-1-thread-2,5,main] task1-2
    //Thread[pool-1-thread-2,5,main] enter in barrier
    //Thread[pool-1-thread-1,5,main] enter in barrier
    //Thread[pool-1-thread-1,5,main]--- task merge result
    //Thread[pool-1-thread-1,5,main] enter out barrier
    //Thread[pool-1-thread-2,5,main] enter out barrier
}
