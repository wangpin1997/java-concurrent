package cn.wpin.concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁demo测试和原理剖析
 *
 * @author wangpin
 */
public class ReentrantReadWriteLockDemo {

    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    public static void main(String[] args) {

        writeLock.lock();
        System.out.println(readWriteLock.getWaitQueueLength(writeLock.newCondition()));
        int x = (1 << 16) - 1;
        int b = 2 >> 17;
        System.out.println(b & x);
        System.out.println((1 >>> 16));
        System.out.println(1<<6);
    }
}
