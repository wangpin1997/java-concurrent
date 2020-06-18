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
public class CyclicBarrierDemo2 {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    private static ExecutorService executorService= Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        System.out.println(cyclicBarrier.getParties()+"-----");
        System.out.println("------"+cyclicBarrier.getNumberWaiting());
        //线程A提交到线程池
        executorService.submit(()->{
            try {
                System.out.println(cyclicBarrier.getParties()+"-----");
                System.out.println("------"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread()+ " thread-A step-1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+ " thread-A step-2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+ " thread-A step-3");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        //线程B提交到线程池
        executorService.submit(()->{
            try {
                System.out.println(cyclicBarrier.getParties()+"-----");
                System.out.println("------"+cyclicBarrier.getNumberWaiting());
                System.out.println(Thread.currentThread()+ " thread-B step-1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+ " thread-B step-2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+ " thread-B step-3");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

//        cyclicBarrier.reset();
        executorService.shutdown();
    }

    //输出结果：可以看出，该屏障是可以重复利用的,保证每一个步骤都是同步
    //Thread[pool-1-thread-1,5,main] thread-A step-1
    //Thread[pool-1-thread-2,5,main] thread-B step-1
    //Thread[pool-1-thread-2,5,main] thread-B step-2
    //Thread[pool-1-thread-1,5,main] thread-A step-2
    //Thread[pool-1-thread-1,5,main] thread-A step-3
    //Thread[pool-1-thread-2,5,main] thread-B step-3
}
