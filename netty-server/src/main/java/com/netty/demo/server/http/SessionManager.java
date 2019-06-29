package com.netty.demo.server.http;

import com.netty.demo.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session 服务管理
 * @Auther: wangjian
 */
public class SessionManager {
    // 存储访问服务器的 Session
    private static final Map<String,HttpSession> SESSION_MAP = new ConcurrentHashMap<String, HttpSession>();

    /**
     * 创建 Session 服务
     * @return
     */
    public static String createSession() {
        DefaultHttpSession httpSession = new DefaultHttpSession();
        String sessionId = httpSession.getSessionId();
        SESSION_MAP.put(sessionId, httpSession);
        return sessionId; // 将创建好的 Session ID 返回到浏览器之中
    }

    /**
     * 获取指定的Session
     * @param sessionId
     * @return
     */
    public static HttpSession getSession(String sessionId) {
        return SESSION_MAP.get(sessionId);
    }

    /**
     * 指定的Session设置为无效
     * @param sessionId
     */
    public static void invalidate(String sessionId) {
        HttpSession httpSession = SESSION_MAP.get(sessionId);
        if (httpSession != null) {
            httpSession.invalidate();
            SESSION_MAP.remove(sessionId);
        }
    }

    /**
     * 判断指定 Session 是否存在
     * @param sessionId
     * @return
     */
    public static boolean isExists(String sessionId) {
        HttpSession httpSession = SESSION_MAP.get(sessionId);
        if (httpSession == null) {
            return false;
        } else {
            if (StringUtils.isBlank(httpSession.getSessionId())) { // 获取到的 SessionId 为空，所以移除该Id,返回 false
                SESSION_MAP.remove(sessionId);
                return false;
            }
            return true;
        }
    }
}
