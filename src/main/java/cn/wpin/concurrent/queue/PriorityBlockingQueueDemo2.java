package cn.wpin.concurrent.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先阻塞队列例子
 * 原理：PriorityBlockingQueue内部使用二叉树堆维护元素优先级，使用数组作为元素存储结构，这个数组是可以扩容的
 * 当当前元素个数超过最大容量，会通过CAS扩容，出队时始终保证出队的是书堆的根节点，而不是停留时间最长的元素，
 * 使用元素的compareTo方法提供默认的优先级比较规则，也可以自定义优先级比较规则，demo1和demo2是两种规则写法
 *
 * @author wangpin
 */
public class PriorityBlockingQueueDemo2 {


    public static void main(String[] args) {
        PriorityBlockingQueue<Task> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Task task = new Task(random.nextInt(10), "taskName" + i);
            priorityBlockingQueue.offer(task);
        }
        while (!priorityBlockingQueue.isEmpty()) {
            Task task = priorityBlockingQueue.poll();
            if (task != null) {
                task.print();
            }
        }
        //输出的都是按照priority升序排列的
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Task implements Comparable<Task> {

        int priority;

        String taskName;

        @Override
        public int compareTo(Task task) {
            return this.priority >= task.getPriority() ? 1 : -1;
        }

        public void print() {
            System.out.println(taskName + " : " + priority);
        }
    }
}
