package com.duck.common.server.core.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author 5duck
 * @date 2021-02-18
 */
@Component
@Slf4j
public class RocketmqProducer {
    @Value("${rocketmq.config.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQProducer producer;

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public void producerInit(String groupName) throws Exception {
        // 实例化消息生产者Producer
        producer = new DefaultMQProducer(groupName);
        // 设置NameServer的地址
        producer.setNamesrvAddr(namesrvAddr);
        // 默认3秒超时，改为5秒
        producer.setSendMsgTimeout(5000);
        // 启动Producer实例
        producer.start();
    }

    @PreDestroy
    public void destory() {
        producer.shutdown();
    }

}
