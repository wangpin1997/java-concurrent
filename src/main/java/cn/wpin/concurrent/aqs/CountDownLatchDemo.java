package cn.wpin.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * countDownLatch 发号枪demo示例
 *
 * 原理，先构造函数声明线程个数，
 * 然后主线程（当然也可以是其他子线程，此方法就是为了保证线程之间在同一起跑线）调用await方法阻塞，待子线程每完成一个任务就调用countdown方法，将state-1，
 * 当state为0，await方法也就会放行，主线程就继续往下走
 *
 * @author wangpin
 */
public class CountDownLatchDemo {

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        long st = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(3);

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }
            // 休息 5 秒后(模拟线程工作了 5 秒)，调用 countDown()
            latch.countDown();
            System.out.println(System.currentTimeMillis() - st + "    t1");
        });

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }
            // 休息 2 秒后(模拟线程工作了 2秒)，调用 countDown()
            latch.countDown();
            System.out.println(System.currentTimeMillis() - st + "    t2");
        });

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }
            // 休息 3 秒后(模拟线程工作了 3 秒)，调用 countDown()

            latch.countDown();
            System.out.println(System.currentTimeMillis() - st + "    t3");
        });

        System.out.println("begin !!!");
        latch.await();

        executorService.execute(() -> {
            try {
                // 阻塞，等待 state 减为 0
                System.out.println("begin !!!");
                latch.await();
                System.out.println("线程 t3 从 await 中返回了");
                System.out.println(System.currentTimeMillis() - st);
            } catch (InterruptedException e) {
                System.out.println("线程 t3 await 被中断");
                Thread.currentThread().interrupt();
            }
        });

//        executorService.execute(() -> {
//            try {
//                // 阻塞，等待 state 减为 0
//                latch.await();
//                System.out.println("线程 t4 从 await 中返回了");
//                System.out.println(System.currentTimeMillis() - st);
//            } catch (InterruptedException e) {
//                System.out.println("线程 t4 await 被中断");
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        executorService.execute(() -> {
//            try {
//                // 阻塞，等待 state 减为 0
//                latch.await();
//                System.out.println("线程 t5 从 await 中返回了");
//                System.out.println(System.currentTimeMillis() - st);
//            } catch (InterruptedException e) {
//                System.out.println("线程 t5 await 被中断");
//                Thread.currentThread().interrupt();
//            }
//        });

        executorService.shutdown();

    }
}