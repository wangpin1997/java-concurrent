package cn.wpin.concurrent.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentHashMap使用注意事项：
 *
 * @author wangpin
 */
public class ConcurrentHashMapDemo {
    //场景：直播
    //描述：在直播业务中，每个直播间对应一个topic，每个用户进入直播间会把自己的设备id绑定在这个topic上
    //也就是一个topic对应多个设备id，则可以使用map来存储该类信息，key为topic，value为设备id list
    //整了半天，就是一个put和putIfAbsent方法的区别，后者存在则不会覆盖，且会返回原本存在的值

    /**
     * 创建一个key为topic，值为设备集合的map
     */
    private static ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap<>(16, 1);

    private static ExecutorService executorService= Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        //线程A
        executorService.execute(()->{
            List<String> list=new ArrayList<>();
            list.add("A-device1");
            list.add("A-device2");
            List<String>oldList=concurrentHashMap.putIfAbsent("topic1",list);
            if (oldList!=null){
                oldList.addAll(list);
            }
            System.out.println(concurrentHashMap.toString());
        });

        //线程B
        executorService.execute(()->{
            List<String> list=new ArrayList<>();
            list.add("B-device1");
            list.add("B-device2");
            List<String>oldList=concurrentHashMap.putIfAbsent("topic1",list);
            if (oldList!=null){
                oldList.addAll(list);
            }
            System.out.println(concurrentHashMap.toString());
        });

        //线程B
        executorService.execute(()->{
            List<String> list=new ArrayList<>();
            list.add("C-device1");
            list.add("C-device2");
            List<String>oldList=concurrentHashMap.putIfAbsent("topic2",list);
            if (oldList!=null){
                oldList.addAll(list);
            }
            System.out.println(concurrentHashMap.toString());
        });

        executorService.shutdown();

    }
}
