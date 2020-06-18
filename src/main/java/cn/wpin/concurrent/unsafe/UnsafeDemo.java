package cn.wpin.concurrent.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe类代码查看以及使用
 *
 * @author wangpin
 */
public class UnsafeDemo {

    /**
     * 获取unsafe实例
     */
    static final Unsafe UNSAFE;

    /**
     * 记录unsafe在该类中的偏移量
     */
    private static final long STATE_OFFSET;

    private  volatile long state = 0;

    static {
        try {
            UNSAFE = createInfo();
            STATE_OFFSET = UNSAFE.objectFieldOffset(UnsafeDemo.class.getDeclaredField("state"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        //创建实例,并把state的值更新为1
        UnsafeDemo unsafeDemo = new UnsafeDemo();
        boolean success = UNSAFE.compareAndSwapInt(unsafeDemo, STATE_OFFSET, 0, 2);
        System.out.println(STATE_OFFSET);
        //输出为2
        System.out.println(unsafeDemo.state);
        //true
        System.out.println(success);
    }

    /**
     * 默认获取unsafe实例写法，会抛异常 java.lang.SecurityException: Unsafe
     *
     * @return
     */
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }

    /**
     * 通过反射来拿到unsafe类，默认的方法只提供给jdk,不给外部用
     *
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Unsafe createInfo() throws Exception {
        Class<?> clazz = Class.forName("sun.misc.Unsafe");
        Field field = clazz.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);
    }
}
