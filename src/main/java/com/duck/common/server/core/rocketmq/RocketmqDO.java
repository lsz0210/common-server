package com.duck.common.server.core.rocketmq;

import lombok.Data;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author 5duck
 * @date 2021-02-18
 */
@Data
public class RocketmqDO {
    private String messageTopic;
    private String messageTag;
    private String messageKey;
    private byte[] messageBody;

    public void setMessageBody(String messageBody) throws UnsupportedEncodingException {
        this.messageBody = messageBody.getBytes(RemotingHelper.DEFAULT_CHARSET);
    }
}
