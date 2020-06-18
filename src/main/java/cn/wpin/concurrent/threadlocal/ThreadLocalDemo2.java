package cn.wpin.concurrent.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 继承性判断
 * 结论：ThreadLocal不支持继承性，父线程取不到子线程的值，子线程也无法取到父线程的值
 *        它对应的是threadLocals属性
 * @author wangpin
 */
public class ThreadLocalDemo2 {

    private static ExecutorService executorService = new ThreadPoolExecutor(3, 10, 30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("wpin-pool").build(),
            new ThreadPoolExecutor.DiscardPolicy());

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        //子线程存值，父线程取值测试
        executorService.execute(() -> {
            //设置线程本地的值
            threadLocal.set("thread1 local value1");
            //调用打印函数
            //在线程内能读取到
            System.out.println("thread1 remove after: " + threadLocal.get());
        });
        executorService.shutdown();
        //在外部的线程无法读取到
        System.out.println(threadLocal.get());

        //父线程存值，子线程取值测试
        threadLocal.set("thread1 local value1");
        executorService.execute(() -> {
            //无法取到父线程值
            System.out.println("thread1 remove after: " + threadLocal.get());
        });
        executorService.shutdown();
        //父线程能读取到值
        System.out.println(threadLocal.get());

    }

}
