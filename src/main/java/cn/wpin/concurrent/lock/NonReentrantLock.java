package cn.wpin.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义一个基于AQS的锁
 *
 * @author wangpin
 */
public class NonReentrantLock implements Lock {

    private final Sync sync=new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
       return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


    /**
     * 内部维护一个Sync类继承 AQS
     */
    public static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 是否持有锁，因为我是不可重入，所以state==1就是持有锁
         *
         * @return
         */
        protected boolean hasLock() {
            return getState() == 1;
        }

        /**
         * 尝试获取锁
         *
         * @param acquire
         * @return
         */
        @Override
        public boolean tryAcquire(int acquire) {
            assert acquire == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;

        }

        /**
         * 释放锁
         *
         * @param acquire
         * @return
         */
        @Override
        public boolean tryRelease(int acquire) {
            assert acquire == 1;
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(Thread.currentThread());
            setState(0);
            return true;

        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }
}
