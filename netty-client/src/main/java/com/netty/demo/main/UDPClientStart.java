package com.netty.demo.main;

import com.netty.demo.client.UDPClient;

/**
 * @Auther: wangjian
 * @Date: 2018-10-08 19:34:04
 */
public class UDPClientStart {

    public static void main(String[] args) {
        System.out.println("*******************监听服务端程序已启动，等待广播**********************");
        new UDPClient().run();
    }
}
