package com.duck.common.server.core.rocketmq;

import com.duck.common.server.core.exceptions.http.ServerErrorException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 5duck
 * @date 2021-02-18
 */
@Component
public class RocketmqOperator {
    @Autowired
    private RocketmqProducer rocketmqProducer;

    /**
     * 同步消息
     *
     * @param rocketmqDO
     * @throws Exception
     */
    public String syncProducer(RocketmqDO rocketmqDO) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {

        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message(rocketmqDO.getMessageTopic(),
                rocketmqDO.getMessageTag(),
                rocketmqDO.getMessageKey(),
                rocketmqDO.getMessageBody()
        );

        // 发送消息到一个Broker
        SendResult sendResult = rocketmqProducer.getProducer().send(msg);
        // 通过sendResult返回消息是否成功送达
        System.out.printf("%s%n", sendResult);
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            return sendResult.getMsgId();
        }else {
            throw new ServerErrorException(10007);
        }

    }

}
