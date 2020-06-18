package cn.wpin.concurrent.clone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息实体类
 *
 * @author wangpin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Msg {


    private String dataId;

    private String body;
}
