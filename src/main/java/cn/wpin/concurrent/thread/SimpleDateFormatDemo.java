package cn.wpin.concurrent.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程不安全
 * SimpleDateFormat是Java提供的日期格式化和解析日期工具，但是他不是线程安全的，在多个线程共用
 * 一个该实例对日期进行格式化和解析时，会导致程序错误
 * <p>
 * 解决办法：
 * 1.每个线程都new一次SimpleDateFormat
 * 2.给sdf对象加锁，保证多线程下只有一个线程能访问
 * 3.使用ThreadLocal,这样每个线程只需要使用自己的实例，没有第一种的开销，，且不需要同步
 *
 * @author wangpin
 */
public class SimpleDateFormatDemo {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static void main(String[] args) {
        errorDemo();
        solution1();
        solution2();
        solution3();
        executorService.shutdown();

    }

    /**
     * 错误示例demo
     */
    private static void errorDemo() {
        //创建10个线程，每个线程共用一个sdf对象对其日期进行解析
        int threadCount = 10;
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println(sdf.parse("2020-06-13 22:30:30"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 第一种解决办法
     */
    private static void solution1() {
        int threadCount = 11;
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(sdf.parse("2020-06-13 22:30:30"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 第二种方法，使用对像锁，保证只有一个线程能访问
     * 亦可换成lock锁
     */
    private static void solution2() {
        int threadCount = 11;
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    synchronized (sdf) {
                        System.out.println(sdf.parse("2020-06-13 22:30:30"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * ThreadLocal来实现
     */
    private static void solution3() {
        int threadCount = 11;
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println(threadLocal.get().parse("2020-06-13 22:30:30"));
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    threadLocal.remove();
                }
            });
        }
    }


}