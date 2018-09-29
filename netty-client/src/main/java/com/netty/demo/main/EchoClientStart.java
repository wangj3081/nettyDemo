package com.netty.demo.main;

import com.netty.demo.client.EchoClient;

/**
 * Created by wangjian on 2018/9/29.
 */
public class EchoClientStart {

    public static void main(String[] args) {
        new EchoClient().run();
    }
}
