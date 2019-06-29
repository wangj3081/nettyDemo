package com.netty.demo.http;

/**
 * @Auther: wangjian
 */
public interface HttpSession {

     String SESSIONID = "HTTPSESSIONID";

    /**
     * 获取属性
     * @param key
     * @return
     */
    Object getAttribute(String key);

    /**
     * 设置属性
     * @param key
     * @param value
     */
    void setAttribute(String key, Object value);

    /**
     * 移除属性
     * @param key
     */
    void removeAttribute(String key);

    /**
     * 设置无效
     */
    void invalidate();

    /**
     * 获取当前的 SessionId
     * @return
     */
    String getSessionId();
}
