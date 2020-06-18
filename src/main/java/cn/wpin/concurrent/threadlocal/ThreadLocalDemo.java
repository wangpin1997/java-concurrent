package cn.wpin.concurrent.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * java并发编程之美开始，之ThreadLocal
 *
 * @author wangpin
 */
public class ThreadLocalDemo {

    private static ExecutorService executorService = new ThreadPoolExecutor(3, 10, 30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("wpin-pool").build(),
            new ThreadPoolExecutor.DiscardPolicy());

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        executorService.execute(() -> {
            //设置线程本地的值
            threadLocal.set("thread1 local value1");
            threadLocal.set("thread1 local value2");
            //调用打印函数
            print("thread1");
            //打印本地变量值
            System.out.println("thread1 remove after: " + threadLocal.get());
        });
        executorService.execute(() -> {
            //设置线程本地的值
            threadLocal.set("thread2 local value2");
            //调用打印函数
            print("thread2");
            //打印本地变量值
            System.out.println("thread2 remove after: " + threadLocal.get());
        });
        executorService.shutdown();

    }


    static void print(String str) {
        System.out.println(str + ":" + threadLocal.get());
        threadLocal.remove();
    }
}
//解析：Thread,ThreadLocal,ThreadLocalMap 三者相关
//1.Thread中维护了一个   ThreadLocal.ThreadLocalMap threadLocals = null;属性，他是 ThreadLocalMap类型的
//2.ThreadLocalMap是ThreadLocal中维护的一个键为Thread,value为值的map,该mapz中的entry实现自WeekReference

//步骤：
//1.threadLocal.set(value),然后拿到当前线程，获取当前线程的threadLocals属性，
// 不为空就set(this,value)this是指当前ThreadLocal对象，value就是我们要存的值
// 为空说明是第一次加载，就初始化threadLocals属性，然后也把当前的对象和value set进去

//2.threadLocal.get(),拿到当前线程，获取到当前线程的threadLocals属性，
//不为空就获取当前对象对应的entry，如果entry不为空，就返回entry.value
//如果threadLocals属性或者entry为空，则初始化当前线程的threadLocals属性setInitialValue()

//3.初始化：先初始化值喂null;然后获取当前线程的threadLocals属性，
//存在就set值，键为当前对象，值位null,,不存在则创建，再初始化键为this,值为null;

//4.remove方法，先获取到当前线程的threadLocals属性，
//如果不为空，就移除当前对象，不然可能会导致内存泄漏
