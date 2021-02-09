package com.duck.common.server.core.aspects;

import com.duck.common.server.core.UnifyResponse;
import com.duck.common.server.core.configuration.ExceptionCodeConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 处理返回结果为 UnifyResponseVO 的 Controller
 * message 默认为 null，在此处通过 code 设置为对应消息
 *
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * @author Juzi@TaleLin
 */
@Aspect
@Component
public class ResultAspect {
    @AfterReturning(returning = "result", pointcut = "execution(public * com.duck.common.server.controller..*.*(..))")
    public void doAfterReturning(UnifyResponse result) {
        int code = result.getCode();
        String oldMessage = result.getMsg();
        // code-message.properties 中配置的 message
        String newMessage = ExceptionCodeConfiguration.getMessage(code);

        // 如果 code-message.properties 中指定了相应的 message 并且 UnifyResponseVO 的 message 为null
        // 则使用 newMessage 替换 oldMessage
        if (StringUtils.isNotBlank(newMessage) && StringUtils.isBlank(oldMessage)) {
            result.setMsg(newMessage);
        }
    }
}
