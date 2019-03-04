package com.netty.demo.main;

import com.netty.demo.server.ObjectServer;

/**
 * Created by wangjian on 2018/9/29.
 */
public class ObjectServerStart {

    public static void main(String[] args) throws Exception {
        System.out.println("*******************服务器正常启动***************");
        new ObjectServer().run();
    }
}
