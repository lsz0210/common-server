package com.duck.common.server.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.duck.common.server.core.LocalUser;
import com.duck.common.server.core.exceptions.http.UnAuthenticatedException;
import com.duck.common.server.core.utils.AnnotationUtil;
import com.duck.common.server.core.utils.JwtToken;
import com.duck.common.server.model.UserInfo;
import com.duck.common.server.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author lsz
 * @Date 2020-08-12 13:55
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private static String startString = "Bearer";
    private static String spaceString = " ";

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> scopeList = getScopeLevel(handler);
        if (scopeList.isEmpty()) {
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken)) {
            throw new UnAuthenticatedException(10002);
        }
        if (!bearerToken.startsWith(startString)) {
            throw new UnAuthenticatedException(10002);
        }
        String[] tokens = bearerToken.split(spaceString);
        if (tokens.length != 2 || StringUtils.isEmpty(tokens[1])) {
            throw new UnAuthenticatedException(10002);
        }
        String token = tokens[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String, Claim> map = optionalMap
                .orElseThrow(() -> new UnAuthenticatedException(10004));
        boolean valid = hasPermission(map, scopeList);
        if (valid) {
            setToThreadLocal(map);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 读取接口对应的权限列表
     *
     * @param handler
     * @return
     */
    private List<String> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation[] annotations = handlerMethod.getMethod().getAnnotations();
            return AnnotationUtil.findRequired(annotations);
        }
        return new ArrayList<>();
    }

    /**
     * 验证权限
     *
     * @param map
     * @param scopeList
     */
    private boolean hasPermission(Map<String, Claim> map, List<String> scopeList) {
        try {
            String scope = map.get("scope").asString();
            List<String> strArray = Arrays.asList(scope.split(","));
            if (strArray.isEmpty()) {
                throw new UnAuthenticatedException(10005);
            }
            Collection intersection = CollectionUtils.intersection(strArray, scopeList);
            if (intersection.isEmpty()) {
                throw new UnAuthenticatedException(10005);
            }
            return true;
        } catch (Exception e) {
            throw new UnAuthenticatedException(10005);
        }
    }

    /**
     * 线程锁写入，防止线程泄露
     *
     * @param map
     */
    private synchronized void setToThreadLocal(Map<String, Claim> map) {
        Long uid = map.get("uid").asLong();
        logger.info("请求用户 ：{}", uid);
        UserInfo userinfo = userInfoService.getById(uid);
        if (userinfo == null) {
            throw new UnAuthenticatedException(20002);
        }
        LocalUser.set(userinfo);
    }
}
