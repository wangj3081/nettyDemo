package com.netty.demo.main;

import com.netty.demo.server.MsgPackServer;

/**
 * Created by wangjian on 2019/1/27.
 */
public class MsgPackServerStart {

    public static void main(String[] args) throws Exception {
        System.out.println("*********************MsgPack服务端启动成功********************");
        new MsgPackServer().run();
    }
}
