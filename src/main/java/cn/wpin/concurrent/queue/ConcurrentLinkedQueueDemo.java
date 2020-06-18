package cn.wpin.concurrent.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * ConcurrentLinkedQueue使用实例
 *
 * @author wangpin
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {
        Queue<String> queue=new ConcurrentLinkedDeque<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        //size方法一定要少用，他会遍历一遍list
        System.out.println(queue.size());
        //判断释放为空，就用isEmpty()
        System.out.println(queue.isEmpty());
    }
}
