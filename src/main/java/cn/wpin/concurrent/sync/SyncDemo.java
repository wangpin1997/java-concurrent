package cn.wpin.concurrent.sync;

/**
 * 研究 synchronized关键字的底层语义
 * @author wangpin
 */
public class SyncDemo {

    private static synchronized void test(){
        System.out.println(123);
    }

    public static void main(String[] args) {
       test();
    }
}
