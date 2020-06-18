package cn.wpin.concurrent.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试，并原理剖析
 *
 * 使用可重入锁来实现一个线程安全的list
 *
 * @author wangpin
 */
public class ReentrantLockList {

    private ArrayList<String> arrayList = new ArrayList<>();

    private static ReentrantLock reentrantLock = new ReentrantLock(true);


    /**
     * 添加元素
     *
     * @param e
     */
    public void add(String e) {
        reentrantLock.lock();
        try {
            arrayList.add(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 删除元素
     *
     * @param e
     */
    public void remove(String e) {
        reentrantLock.lock();
        try {
            arrayList.remove(e);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 获取元素
     *
     * @param e
     * @return
     */
    public String get(String e) {
        reentrantLock.lock();
        try {
            return arrayList.get(arrayList.indexOf(e));
        } finally {
            reentrantLock.unlock();
        }
    }


    /**
     * 获取元素
     *
     * @param index
     * @return
     */
    public String get(int index) {
        reentrantLock.lock();
        try {
            return arrayList.get(index);
        } finally {
            reentrantLock.unlock();
        }
    }
}
