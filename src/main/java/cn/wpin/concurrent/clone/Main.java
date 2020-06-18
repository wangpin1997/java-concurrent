package cn.wpin.concurrent.clone;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 深复制测试
 *
 * 目的：本次就是通过一个例子来演示需要复用但是会被下游操作的数据，需要进行深复制。
 * 发送两次消息，第一次发送后，消息主题改变了，导致第二次发送的消息主题也跟着第一次改变而改变，不合理
 * 还有集合的构造方法传参，也是两者引用一份数据，也是浅复制，最后还是根据BeanUtils.cloneBean来完成深复制
 *
 * @author wangpin
 */
public class Main {

    static Map<Integer, StrategyService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(111, new StrategyOneService());
        serviceMap.put(222, new StrategyTwoService());
    }

    public static void main(String[] args) {
        Map<Integer, List<String>> appKeyMap = new HashMap<>(4, 1F);

        //创建appKey==1的设备列表
        List<String> oneList = new ArrayList<>();
        oneList.add("device_id_1");
        appKeyMap.put(111, oneList);

        //创建appKey==1的设备列表
        List<String> twoList = new ArrayList<>();
        twoList.add("device_id_2");
        appKeyMap.put(222, twoList);

        //创建消息
        List<Msg> msgList = new ArrayList<>();
        msgList.add(Msg.builder().dataId("abc").body("hello").build());

        Map<Integer, List<Msg>> msgMap = new HashMap<>(4, 1F);


        //①
        //发送结果不对
        //通过集合构造方法，两个集合执行的是一份对象实例，修改数据后两者都可见
        for (int appKey : appKeyMap.keySet()) {
            msgMap.put(appKey, new ArrayList<>(msgList));
        }

        //②
        //发送结果不对
        //共享一份数据，所以导致第二次发送的时候，数据已经被第一次修改了
        for (int appKey : appKeyMap.keySet()) {
            StrategyService strategyService = serviceMap.get(appKey);
            if (null != strategyService) {
                strategyService.sendMsg(msgList, appKeyMap.get(appKey));
            } else {
                System.out.println(String.format("appKey:%s,is not register service", appKey));
            }
        }

        //③
        //进行深复制，结果正确
        for (Integer integer : appKeyMap.keySet()) {
            List<Msg> msgs = new ArrayList<>();
            for (Msg value : msgList) {
                Msg msg = null;
                try {
                    //使用BeanUtils对对象进行深复制
                    msg = (Msg) BeanUtils.cloneBean(value);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (null != msg) {
                    msgs.add(msg);
                }
            }
            msgMap.put(integer, msgs);
        }

        for (int appKey : appKeyMap.keySet()) {
            StrategyService strategyService = serviceMap.get(appKey);
            if (null != strategyService) {
                strategyService.sendMsg(msgMap.get(appKey), appKeyMap.get(appKey));
            } else {
                System.out.println(String.format("appKey:%s,is not register service", appKey));
            }
        }


    }
}
