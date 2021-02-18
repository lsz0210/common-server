package com.duck.common.server.controller;

import com.duck.common.server.core.UnifyResponse;
import com.duck.common.server.core.rocketmq.RocketmqDO;
import com.duck.common.server.core.rocketmq.RocketmqOperator;
import com.duck.common.server.core.rocketmq.RocketmqProducer;
import com.duck.common.server.core.utils.RedisOperator;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 5duck
 * @date 2021-02-13
 */
@RestController
@RequestMapping("")
public class TestController {
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RocketmqOperator rocketmqOperator;

    @GetMapping("/redisGet")
    public UnifyResponse redis() {
        redisOperator.set("key1", "hello");
        return new UnifyResponse<>(0, "success", redisOperator.get("key1"));
    }

    @PostMapping("/rocketmqProducer")
    public UnifyResponse rocketmqProducer(@RequestBody RocketmqDO rocketmqDO) {
        try {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(rocketmqDO.getMessageTopic(),
                    rocketmqDO.getMessageTag(),
                    rocketmqDO.getMessageKey(),
                    rocketmqDO.getMessageBody()
            );
            return new UnifyResponse<>(0, "success", rocketmqOperator.syncProducer(msg));
        } catch (Exception e) {
            e.printStackTrace();
            return new UnifyResponse(10007);
        }
    }

}
