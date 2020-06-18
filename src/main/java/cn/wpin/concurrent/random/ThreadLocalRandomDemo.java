package cn.wpin.concurrent.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * ThreadLocalRandom使用demo，并剖析原理
 * 问题：ThreadLocalRandom工作原理
 *  ThreadLocalRandom继承自Random,重写了nextInt()方法，大致原理跟ThreadLocal一样
 *  Thread类中维护了如下①，②，③三个变量，都加上了@Contended注解，是为了防止出现伪共享 ，value一样是为了确保存到一个内存行中
 *  线程通过ThreadLocalRandom.current();初始化，通过nextInt()取值，
 *  然后就去拿对应Thread类中的种子，第一次是直接计算。后面的就会把种子存到Thread类中
 *  这样就不狐疑出现多i个线程同时修改一个种子，而造成资源的浪费。
 *
 * @author wangpin
 */
public class ThreadLocalRandomDemo {

    public static void main(String[] args) {
        ThreadLocalRandom random=ThreadLocalRandom.current();
        System.out.println(random.nextInt());
    }




    //①
    /** The current seed for a ThreadLocalRandom */
    @sun.misc.Contended("tlr")
    long threadLocalRandomSeed;

    //②
    /** Probe hash value; nonzero if threadLocalRandomSeed initialized */
    @sun.misc.Contended("tlr")
    int threadLocalRandomProbe;

    //③
    /** Secondary seed isolated from public ThreadLocalRandom sequence */
    @sun.misc.Contended("tlr")
    int threadLocalRandomSecondarySeed;

}
