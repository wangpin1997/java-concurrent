package cn.wpin.concurrent.clone;

import java.util.List;

/**
 * 策略一
 *
 * @author wangpin
 */
public class StrategyOneService implements StrategyService {
    @Override
    public void sendMsg(List<Msg> msgList, List<String> deviceList) {

        for (Msg msg : msgList) {
            msg.setDataId("OneService_" + msg.getDataId());
            System.out.println(msg.getDataId() + "---" + deviceList);

        }

    }
}
