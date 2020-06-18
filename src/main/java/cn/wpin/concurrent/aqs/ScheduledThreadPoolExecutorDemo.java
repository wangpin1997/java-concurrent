package cn.wpin.concurrent.aqs;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用ScheduledThreadPoolExecutor来替代Timer功能
 *
 * 对比Timer好处：不会因为其他一个子线程执行抛出异常而导致其他线程执行失败
 * 原因：ScheduledThreadPoolExecutor的其他任务之所以不受异常的任务影响，是因为在ScheduledThreadPoolExecutor中
 * 的ScheduledFutureTask任务中catch了异常
 *
 * 功能：包含但不局限于Timer,Timer是固定的单消费、多生产模式，但是ScheduledThreadPoolExecutor是可以配置的，既可以
 * 单消费，也可多消费，开发过程中尽量先用这个
 *
 * @author wangpin
 */
public class ScheduledThreadPoolExecutorDemo {

    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());

    public static void main(String[] args) {
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("---one task---");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("error");
        }, 500, TimeUnit.MICROSECONDS);

        Optional<Integer> threadNum = Optional.of(2);
        scheduledThreadPoolExecutor.schedule(() -> {
            for (int i = 0; i < threadNum.get(); i++) {
                System.out.println("---two task---");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, 1000, TimeUnit.MICROSECONDS);

        scheduledThreadPoolExecutor.shutdown();
    }

}
