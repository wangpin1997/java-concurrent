package cn.wpin.concurrent.random;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试一下Random,并剖析其原理
 * 问题：Random大致原理就是通过设置一个种子，然后来计算出值，然后再通过CAS+do while循环来设置随机数
 *  多线程情况下，失败了的线程会继续循环CAS尝试，所以Random类在多线程情况效率极低。所以请看ThreadLocalRandom
 *
 * @author wangpin
 */
public class RandomDemo {

    static AtomicLong longSeed=new AtomicLong(5);

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;


    public static void main(String[] args) {
        Random random=new Random();
        System.out.println(random.nextInt());
        System.out.println(random.nextInt(10));
        System.out.println(initialScramble(5));
        // 101    -5
        //1111
        System.out.println(5^15);//只有一个为零
        System.out.println(5&15);//都不为零
        System.out.println(5|15);//有一个不为零即可
        System.out.println(5*15);//有一个不为零即可
        System.out.println(next(6));
    }

    private static long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }

    protected static int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed = longSeed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed.compareAndSet(oldseed, nextseed));
        return (int)(nextseed >>> (48 - bits));
    }
}
