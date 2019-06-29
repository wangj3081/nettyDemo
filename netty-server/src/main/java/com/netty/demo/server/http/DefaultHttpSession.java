package com.netty.demo.server.http;

import com.netty.demo.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Session 实现
 * @Auther: wangjian
 */
public class DefaultHttpSession implements HttpSession {

    private String sessionId;

    private Map<String, Object> attributeMap = new HashMap<String, Object>();

    public DefaultHttpSession() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public Object getAttribute(String key) {
        return this.attributeMap.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attributeMap.put(key, value);
    }

    public void removeAttribute(String key) {
        this.attributeMap.remove(key);
    }

    public void invalidate() {
        this.sessionId = null; // 销毁
        this.attributeMap.clear();
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
