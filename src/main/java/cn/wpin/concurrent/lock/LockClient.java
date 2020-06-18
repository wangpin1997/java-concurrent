package cn.wpin.concurrent.lock;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试自定义的锁
 *
 * @author wangpin
 */
public class LockClient {

    private final static NonReentrantLock LOCK = new NonReentrantLock();

    private final static Condition NOT_FULL = LOCK.newCondition();

    private final static Condition NOT_EMPTY = LOCK.newCondition();

    private final static Queue<String> QUEUE = new LinkedBlockingDeque<>();

    private final static int QUEUE_SIZE = 10;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        EXECUTOR_SERVICE.execute(() -> {
            LOCK.lock();
            try {
                while (QUEUE.size() == QUEUE_SIZE) {
                    NOT_EMPTY.await();
                }
                System.out.println("add");
                QUEUE.add("wpin");
                NOT_FULL.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });
        EXECUTOR_SERVICE.execute(() -> {
            LOCK.lock();
            try {
                while (QUEUE.size() == 0) {
                    NOT_FULL.await();
                }
                System.out.println("delete");
                QUEUE.poll();
                NOT_EMPTY.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });
//        EXECUTOR_SERVICE.shutdown();
    }

}
