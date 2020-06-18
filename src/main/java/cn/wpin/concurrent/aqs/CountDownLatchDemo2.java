package cn.wpin.concurrent.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * 共享模式，让所有线程在同一起点，不重用
 * @author wangpin
 */
public class CountDownLatchDemo2 {

    private static CountDownLatch countDownLatch=new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Task(countDownLatch),"t1").start();
        new Thread(new Task(countDownLatch),"t2").start();
        new Thread(new Task(countDownLatch),"t3").start();
        countDownLatch.await();
        //只有当三个都准备好了在同时开始输出
        //调用一次countDown()就-1，只有为0才开始
        System.out.println("所以线程准备就绪，预备，跑！");
    }



    static class Task implements Runnable{

        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("当前线程{"+Thread.currentThread().getName()+"}已到达");
            countDownLatch.countDown();
        }
    }
}
