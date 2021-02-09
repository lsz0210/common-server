/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-03-21 15:38
 */
package com.duck.common.server.core;


import com.duck.common.server.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class LocalUser {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();


    public static void set(UserInfo user) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        LocalUser.threadLocal.set(map);
    }

    public static void clear() {
        LocalUser.threadLocal.remove();
    }

    public static UserInfo getUser() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (UserInfo) map.get("user");
    }

}
