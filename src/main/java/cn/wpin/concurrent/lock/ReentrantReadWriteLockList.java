package cn.wpin.concurrent.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁来实现线程安全的list
 *
 * 添加删除使用写锁，查询使用读锁，大部分场景是读居多，所以效果更佳
 *
 * @author wangpin
 */
public class ReentrantReadWriteLockList {

    private ArrayList<String> arrayList = new ArrayList<>();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    static Lock readLock = readWriteLock.readLock();

    static Lock writeLock = readWriteLock.writeLock();


    /**
     * 添加元素
     *
     * @param e
     */
    public void add(String e) {
        writeLock.lock();
        try {
            arrayList.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 删除元素
     *
     * @param e
     */
    public void remove(String e) {
        writeLock.lock();
        try {
            arrayList.remove(e);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取元素
     * @param e
     * @return
     */
    public String get(String e) {
        readLock.lock();
        try {
            return arrayList.get(arrayList.indexOf(e));
        } finally {
            readLock.unlock();
        }
    }


    /**
     * 获取元素
     * @param index
     * @return
     */
    public String get(int index) {
        readLock.lock();
        try {
            return arrayList.get(index);
        } finally {
            readLock.unlock();
        }
    }


}
