package cn.wpin.concurrent.aqs;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer类使用注意事项
 *
 * 存在的问题：当第一个子线程抛出 异常的时候，第二个就会执行失败，所以这是Timer存在的一个问题
 * 更好的方案可以用ScheduledThreadPoolExecutor替代。
 *
 * Timer是ScheduledThreadPoolExecutor固定的单消费、多生产模式，功能局限
 *
 * @author wangpin
 */
public class TimerDemo {


    private static Timer timer = new Timer();

    public static void main(String[] args) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("---one task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("error");
            }
        }, 500);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("---two task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }
}
