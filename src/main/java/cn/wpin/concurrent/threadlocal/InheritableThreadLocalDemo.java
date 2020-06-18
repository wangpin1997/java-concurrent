package cn.wpin.concurrent.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 可继承的ThreadLocal
 * 结论：InheritableThreadLocal继承于ThreadLocal，它实现了子线程能读取到父线程的值，父线程还是无法读取到子线程的值的
 *      他对应的是Thread的inheritableThreadLocals属性
 * @author wangpin
 */
public class InheritableThreadLocalDemo {

    private static ExecutorService executorService = new ThreadPoolExecutor(3, 10, 30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("wpin-pool").build(),
            new ThreadPoolExecutor.DiscardPolicy());

    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        //子线程存值，父线程取值测试
        executorService.execute(() -> {
            //设置线程本地的值
            threadLocal.set("thread1 local value1");
            //调用打印函数
            //在线程内能读取到
            System.out.println("thread1 remove after: " + threadLocal.get());
        });
        threadLocal.set("thread1 local value1");

        //在外部的线程无法读取到
        System.out.println(threadLocal.get());

        //父线程存值，子线程取值测试
        threadLocal.set("thread1 local value1");
        executorService.execute(() -> {
            //能读取到父线程值
            System.out.println("thread1 remove after: " + threadLocal.get());
        });
        executorService.shutdown();
        //父线程能读取到值
        System.out.println(threadLocal.get());

    }
}
//1.在我们主线程（main)方法中，会调用到new Thread()方法，然后就会进入其init()方法
//当parent.inheritableThreadLocals(主线程的)不为空就ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
//然后进入到createInheritedMap()方法。进入到ThreadLocalMap(ThreadLocalMap parentMap)的构造方法，
//取出父线程的entry数组，遍历，拿到的value存到当前子线程中。即可。
