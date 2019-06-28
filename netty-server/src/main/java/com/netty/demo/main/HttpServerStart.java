package com.netty.demo.main;

import com.netty.demo.server.HttpServer;

/**
 * @Auther: wangjian
 */
public class HttpServerStart {

    public static void main(String[] args) throws Exception {
        System.out.println("**********************服务启动，等待访问************************");
        new HttpServer().run();
    }
}
