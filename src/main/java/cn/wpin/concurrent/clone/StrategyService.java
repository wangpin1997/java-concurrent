package cn.wpin.concurrent.clone;

import java.util.List;

/**
 * 发送消息策略 父接口
 *
 * @author wangpin
 */
public interface StrategyService {

    void sendMsg(List<Msg> msgList, List<String> deviceList);
}
