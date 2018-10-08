package com.netty.demo.main;

import com.netty.demo.server.UDPServer;

/**
 * @Auther: wangjian
 * @Email: wangjian@qihaiyun.com
 * @Date: 2018-10-08 19:34:53
 */
public class UDPServerStart {

    public static void main(String[] args) {
        new UDPServer().run();
    }
}
