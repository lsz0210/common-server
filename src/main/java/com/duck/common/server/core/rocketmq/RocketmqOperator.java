package com.duck.common.server.core.rocketmq;

import com.duck.common.server.core.exceptions.http.ServerErrorException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
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
     * @param msg
     * @throws Exception
     */
    public String syncProducer(Message msg) throws Exception {

        // 发送消息到一个Broker
        SendResult sendResult = rocketmqProducer.getProducer().send(msg);
        // 通过sendResult返回消息是否成功送达
        System.out.printf("%s%n", sendResult);
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            return sendResult.getMsgId();
        } else {
            throw new ServerErrorException(10007);
        }
    }

    /**
     * 异步消息推送
     *
     * @param msg
     * @throws RemotingException
     * @throws MQClientException
     * @throws InterruptedException
     */
    public void asyncProducer(Message msg) throws RemotingException, MQClientException, InterruptedException {
        rocketmqProducer.getProducer().setRetryTimesWhenSendAsyncFailed(0);
        // SendCallback接收异步返回结果的回调,回调中添加业务处理
        rocketmqProducer.getProducer().send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf(" asyncProducer success %s %n", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                System.out.printf(" Exception %s %n", e);
                e.printStackTrace();
            }
        });
    }

    /**
     * 单向推送
     * @param msg
     * @throws RemotingException
     * @throws MQClientException
     * @throws InterruptedException
     */
    public void oneWayProducer(Message msg)throws RemotingException, MQClientException, InterruptedException {

        rocketmqProducer.getProducer().sendOneway(msg);
    }

}
