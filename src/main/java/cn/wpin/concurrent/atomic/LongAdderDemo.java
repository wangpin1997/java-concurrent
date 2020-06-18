package cn.wpin.concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * 测试一下LongAdder类，并剖析其原理
 * 问题：该类是为了弥补AtomicLong在多线程情况下效率低下而新推出的一个类
 * LongAdder是LongAccumulator类的一个特殊例子，LongAccumulator提供更多的运算规则
 * 而LongAdder则是默认的加法。
 * LongAdder的原理是继承自Striped64，而Striped64内部维护了一个Cell类，他里边的value是可见性的
 * 递增也是cas的，所以该类的操作是线程安全的。LongAdder的原理就是将一个数分成一个base+cells数组
 * 一个线程修改其中的一个，然后整个cells累加,再加上base,就是我们需要的值
 *
 * @author wangpin
 */
public class LongAdderDemo {

    static LongAdder adder=new LongAdder();
    public static void main(String[] args) {
        adder.increment();

    }

}
