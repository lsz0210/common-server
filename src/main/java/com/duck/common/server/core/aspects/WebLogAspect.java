package com.duck.common.server.core.aspects;

import com.duck.common.server.core.servlet.RequestWrapper;
import com.duck.common.server.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author 5duck
 * @date 2021-01-30
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 进入方法时间戳
     */
    private Long startTime;
    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;

    public WebLogAspect() {
    }

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(public * com.duck.common.server.controller..*.* (..))")
    public void webLogPointcut() {
    }

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @throws Throwable
     */
    @Before("webLogPointcut()")
    public void doBefore() throws IOException {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        //打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("请求信息 ： {}", JsonUtil.objectToJson(
                WebLogInput.builder()
                        .startTime(startTime)
                        .url(request.getRequestURI())
                        .method(request.getMethod())
                        .addr(request.getRemoteAddr())
                        .urlParam(JsonUtil.objectToJson(request.getParameterMap()))
                        .requestParam(requestWrapper.getBody())
                        .build())
        );
    }

    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 1. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLogPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.info("请求返回 ： {}", JsonUtil.objectToJson(
                WebLogOutput.builder()
                        .endTime(endTime)
                        .useTime(endTime - startTime)
                        .responseParam(JsonUtil.objectToJson(ret))
                        .build()
        ));
    }

    /**
     * 异常通知：
     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     * 1. 在异常通知中设置异常信息，并将其保存
     *
     * @param throwable
     */
    @AfterThrowing(value = "webLogPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 保存异常日志记录
        log.error("发生异常时间：{}", System.currentTimeMillis());
        log.error("抛出异常：{}", Arrays.toString(throwable.getStackTrace()));
    }
}
