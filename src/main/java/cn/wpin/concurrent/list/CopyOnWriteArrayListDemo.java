package cn.wpin.concurrent.list;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试一下CopyOnWriteArrayList,并剖析其原理
 * 问题：该集合大概的套路呢是 添加，还是删除操作,先上锁，然后执行操作，最后释放锁。他是基于数组赋值来实现的
 * 内部维护了一个ReentrantLock 来保证其线程安全性，如果是在指定下标添加或者删除，则原数组分段赋值过去
 *
 * 弱一致性：如下在子线程中已经替换掉了下标为1的元素和删除了下标为2，3的元素
 * 但是在下面的迭代器中，还是完好无损的保留着之前的元素，因为迭代器中存在着原数组的引用，当原数组被改变时候，
 * 在迭代器中的引用是指向原来的数组的，所以就被说成了快照，所以这也就是其线程隔离性，弱一致性。
 * 当然，如果迭代器是在子线程操作后的下面获取的，就是拿到的是改变后的数组了
 *
 * @author wangpin
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list=new CopyOnWriteArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        //已经存在就不添加
        list.addIfAbsent(20);
        //因为是在子线程执行之前获取的，所以保持着修改前的数组快照
        Iterator iterable=list.iterator();

        Thread thread=new Thread(()->{
            list.set(1,100);
            list.remove(2);
            list.remove(3);
        });
        thread.start();
        thread.join();
        Iterator iterable2=list.iterator();
        while (iterable.hasNext()){
            System.out.println(iterable.next());
        }

        while (iterable2.hasNext()){
            System.out.println(iterable2.next());
        }
        System.out.println(list);
    }
}
