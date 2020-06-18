package cn.wpin.concurrent.clone;

import java.util.List;

/**
 * 策略二
 *
 * @author wangpin
 */
public class StrategyTwoService implements StrategyService {

    @Override
    public void sendMsg(List<Msg> msgList, List<String> deviceList) {

        for (Msg msg : msgList) {
            msg.setDataId("TwoService_" + msg.getDataId());
            System.out.println(msg.getDataId() + "---" + deviceList);

        }

    }
}
