package cn.wpin.concurrent.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先队列
 *
 * @author wangpin
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        //自定义了一个比较器，poll就根据比较得字段输出
        PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<>(10, new Compare<>());
        queue.add(new User(1,"王品",22));
        queue.add(new User(1,"王小品",24));
        queue.add(new User(1,"王大品",23));
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    /**
     * 自定义比较器
     */
    static class Compare<T> implements Comparator<T> {

        @Override
        public int compare(T o1, T o2) {
            User user1 = (User) o1;
            User user2 = (User) o2;
            return Integer.compare(user1.getAge(), user2.getAge());
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class User {
        int id;
        String name;
        int age;
    }
}
