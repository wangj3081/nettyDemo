package com.netty.demo.main;

import com.netty.demo.client.EchoClient;
import com.netty.demo.client.ObjectClient;

/**
 * Created by wangjian on 2018/9/29.
 */
public class ObjectClientStart {

    public static void main(String[] args) {
        new ObjectClient().run();
    }
}
