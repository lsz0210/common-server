package com.duck.common.server.core.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费者服务，原则上应该与生产者服务放在不同的java服务中启动
 * @author 5duck
 * @date 2021-02-18
 */
@Component
@Slf4j
public class RocketmqConsumer {
    @Value("${rocketmq.config.namesrvAddr}")
    private String namesrvAddr;

    /**
     * 消费消息
     *
     * @throws Exception
     */
    public void consumerInit(String groupName, String topic) throws Exception {

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);

        // 设置NameServer的地址
        consumer.setNamesrvAddr(namesrvAddr);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(topic, "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt message : msgs) {
                    String tags = message.getTags();
                    String body = new String(message.getBody());
                    try{
                        log.info("Msg tag : " + tags);
                        log.info("Msg body : " + body);
                    }catch (Exception e){
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("RocketMq Consumer Started.%n");
    }
}
