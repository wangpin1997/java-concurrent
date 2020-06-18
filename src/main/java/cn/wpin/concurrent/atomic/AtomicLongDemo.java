package cn.wpin.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试一下AtomicLong类，并剖析其原理
 * 问题：AtomicLong是根据unsafe类提供的CAS操作来提供原子性操作，但是同时也会导致效率低下，消耗大，浪费CPU
 * 所以就出现了LongAdder类
 *
 * @author wangpin
 */
public class AtomicLongDemo {

    /**
     * 默认是不支持这样创建的，我图个方便
     */
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    static AtomicLong atomicLong = new AtomicLong(0);
    static Long aLong = 0L;

    private static Integer[] arr1 = new Integer[]{1, 0, 8, 0, 2, 0, 9};
    private static Integer[] arr2 = new Integer[]{1, 0, 8, 0, 2, 20, 10};

    public static void main(String[] args) throws InterruptedException {
        executorService.execute(() -> {
            for (Integer integer : arr1) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                    aLong++;
                }
            }
        });
        executorService.execute(() -> {
            for (Integer integer : arr2) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                    aLong++;
                }
            }
        });
        Thread.sleep(2000);
        System.out.println("原子类AtomicLong:" + atomicLong.get());
        System.out.println("非原子类Long:" + aLong);
        executorService.shutdown();
    }
}
