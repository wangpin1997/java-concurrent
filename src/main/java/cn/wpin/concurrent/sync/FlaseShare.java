package cn.wpin.concurrent.sync;

import sun.misc.Contended;

/**
 * 解决伪共享
 * 1.jdk8之前采用字节填充的方式来解决，对象包含三个部分，对象头、示例数据、对齐填充
 * 假如缓存行的大小为64K，
 * 对象头占用8K,实例数据根据用户来定义，不超过56K，少了就由字节填充部分来进行补充，
 * 确保一个缓存行中只有一个对象
 *
 * @author wangpin
 */
@Contended
public class FlaseShare {

    private volatile long value = 0L;
    private long p1, p2, p3, p4, p5, p6;

    @Contended
    private long threadValue;

    //jdk8之前的方式
    //如这所示，假如缓存行大小为64K，long为8个字节，6个long型填充+一个long型value+一个对象头8k，刚好64K，
    // 确保数据不会有两个对象落在同一缓存行，避免伪共享。

    //jdk8之后的方式
    //通过@Contended注解自动进行填充,可以作用于类上，也可以作用于属性
    //在Thread类中的threadLocalRandom的属性中有体现
    //这个注解默认情况下只用于rt包下面，咱们可以通过添加jvm参数 -XX:RestrictContended
    //填充的宽度默认为128，可以通过设置 -XX:ContendedPaddingWidth 参数来修改

    /** The current seed for a ThreadLocalRandom */
    @sun.misc.Contended("tlr")
    long threadLocalRandomSeed;

    /** Probe hash value; nonzero if threadLocalRandomSeed initialized */
    @sun.misc.Contended("tlr")
    int threadLocalRandomProbe;

    /** Secondary seed isolated from public ThreadLocalRandom sequence */
    @sun.misc.Contended("tlr")
    int threadLocalRandomSecondarySeed;
}
