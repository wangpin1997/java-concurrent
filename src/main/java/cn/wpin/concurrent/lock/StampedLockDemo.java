package cn.wpin.concurrent.lock;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.StampedLock;

/**
 * Doug lea 给出的例子
 * <p>
 * 解答：StampedLock是对读写锁的一个优化，在jdk8中，他不是可重入锁。但是可以乐观读锁在多线程多读的环境下提供了更好的性能
 * 因为获取乐观读锁的时候，不需要CAS操作设置锁的状态
 *
 * @author wangpin
 **/
public class StampedLockDemo {

    /**
     * 定义两个成员变量
     */
    private Double x=2.0, y=3.0;

    /**
     * 定义锁
     */
    private final StampedLock stampedLock = new StampedLock();

    /**
     * 排它锁---写锁
     *
     * @param a
     * @param b
     */
    public void move(Double a, Double b) {
        long stamp = stampedLock.writeLock();
        x += a;
        y += b;
        stampedLock.unlockWrite(stamp);
    }

    /**
     * 乐观读锁
     *
     * @return
     */
    public double distanceFromOrigin() { //
        //尝试获取乐观读锁
        long stamp = stampedLock.tryOptimisticRead();
        double currentX = x, currentY = y;
        //检查获取了读锁戳记后，锁有没有被其他线程抢占
        if (!stampedLock.validate(stamp)) {
            //如果被抢占了，则获取一个共享读锁（悲观获取）
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                //释放锁
                stampedLock.unlockRead(stamp);
            }
        }
        //返回计算结果
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }


    /**
     * 使用悲观锁获取读锁，并尝试转换成写锁
     *
     * @param newX
     * @param newY
     */
    public void moveIfAtOrigin(double newX, double newY) {

        // 这里可以使用乐观读锁来替代
        //long stamp = stampedLock.tryOptimisticRead();
        long stamp = stampedLock.readLock();
        try {
            //如果当前点在原点则移动
            while (x == 0.0 && y == 0.0) {
                //尝试将读锁升级成写锁
                long ws = stampedLock.tryConvertToWriteLock(stamp);
                //升级成功，则更新戳记，并返回坐标值，推出循环
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    //升级失败，手动释放读锁，强行获取写锁，然后循环尝试
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
            //释放锁
            stampedLock.unlock(stamp);
        }
    }

    public static void main(String[] args) {
        AtomicLong atomicLong=new AtomicLong(10);
        System.out.println(atomicLong.getAndDecrement());

        System.out.println(Integer.toBinaryString(255));
        System.out.println(Integer.toBinaryString(256));
        System.out.println(255&256);
        new StampedLockDemo().move(1.0,2.0);
    }

}
