package cn.wpin.concurrent.queue;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列测试
 * 延迟队列，内部是用PriorityQueue存放数据，使用ReentrantLock实现同步，里边的元素需要实现Delayed接口
 * 重写两个方法，1.getDelay获取剩余时间，compareTo定义比较规则
 *
 * @author wangpin
 */
public class DelayQueueDemo {


    public static void main(String[] args) {
        DelayQueue<DelayNode> delayNodes = new DelayQueue<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            DelayNode node = new DelayNode(random.nextInt(500), "task:" + i);
            delayNodes.offer(node);
        }
        DelayNode node=null;
        try {
            for (; ; ) {
                while ((node = delayNodes.take()) != null) {
                    System.out.println(node.toString());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Data
    @ToString
    @NoArgsConstructor
    static class DelayNode implements Delayed {


        /**
         * 延迟时间
         */
        private long delayTime;

        /**
         * 过期时间
         */
        private long expire;

        /**
         * 任务名称
         */
        private String taskName;

        public DelayNode(long delay, String taskName) {
            this.delayTime = delay;
            this.taskName = taskName;
            expire = System.currentTimeMillis() + delay;
        }

        /**
         * 剩余时间=到期时间-当前时间
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
        }

        /**
         * 有限队列的比较规则
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MICROSECONDS) - o.getDelay(TimeUnit.MICROSECONDS));
        }

    }
}
