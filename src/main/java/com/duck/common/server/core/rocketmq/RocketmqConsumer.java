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
                log.info("Consume start time :{}", System.currentTimeMillis());
                for (MessageExt message : msgs) {
                    String tags = message.getTags();
                    String body = new String(message.getBody());
                    System.out.println("Msg tag : " + tags);
                    System.out.println("Msg body : " + body);
                }
                log.info("Consume end time :{}", System.currentTimeMillis());
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
