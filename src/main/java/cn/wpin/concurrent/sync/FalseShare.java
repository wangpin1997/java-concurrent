package cn.wpin.concurrent.sync;

/**
 * 伪共享 测试
 *
 * @author wangpin
 */
public class FalseShare {

    private static int LINE_NUM = 10240;

    private static int COL_NUM = 10240;


    public static void main(String[] args) {
        useCache();  //88  88  90
        nonUseCache();  //1491  1501  1545
        //三组数据可总结，没有用到共享缓存行的效率比用到的慢多了
        //1.因为数组内元素是连续存储的，当访问第一个元素的时候，会把元素后若干个元素放入缓存行中。
        //而不需要再去主存中读取，后面也是如此，类似于装水一桶一桶装，而不在缓存行就是一瓢一瓢的装。
        //2.下面是跳跃式访问的，这破环了程序访问的局部性原则，切缓存是有容量控制的，当缓存满了就会被淘汰算法淘汰掉
        //就会导致缓存行中的元素还没被读完就被淘汰掉了
    }

    /**
     * 顺序读取，相当于是用到了缓存行
     */
    private static void useCache() {
        long[][] array = new long[LINE_NUM][COL_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COL_NUM; j++) {
                array[i][j] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 跳跃读取，没有用到缓存行，执行时间长
     */
    private static void nonUseCache() {
        long[][] array = new long[LINE_NUM][COL_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < COL_NUM; i++) {
            for (int j = 0; j < LINE_NUM; j++) {
                array[j][i] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
