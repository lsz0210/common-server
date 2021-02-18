package com.duck.common.server.core.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 5duck
 * @date 2021-02-18
 */
@Component
@Slf4j
public class RocketmqService implements CommandLineRunner {
    @Value("${rocketmq.config.groupName}")
    private String groupName;

    @Value("${rocketmq.config.messageTopic}")
    private String messageTopic;

    @Autowired
    private RocketmqProducer rocketmqProducer;

    @Autowired
    private RocketmqConsumer rocketmqConsumer;

    @Override
    public void run(String... args) throws Exception {
        log.info("==== producer service init ====");
        rocketmqProducer.producerInit(groupName);
        log.info("==== consumer service init ====");
        rocketmqConsumer.consumerInit(groupName, messageTopic);
    }
}
