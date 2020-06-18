package cn.wpin.concurrent.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池中测试ThreadLocal使用出现内存泄露
 * <p>
 * 通过对比使用了remove和没有使用remove，看出堆中的内存变化情况，所以使用了ThreadLocal后，一定要及时清理
 *
 * @author wangpin
 */
public class ThreadPoolDemo {

    private static class LocalVariable {
        private Long[] a = new Long[1024 * 1024];
    }

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("wpin-pool").build();
    private final static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);

    private static final ThreadLocal<LocalVariable> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        int count = 50;
        for (int i = 0; i < count; i++) {
            EXECUTOR.execute(() -> {
                THREAD_LOCAL.set(new LocalVariable());
                System.out.println("use localVariable");
                THREAD_LOCAL.remove();
            });
            Thread.sleep(1000);
        }
        System.out.println(" thread pool execute over ");
    }
}
