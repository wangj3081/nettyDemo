package com.netty.demo.client;

import com.netty.demo.api.ServerInfo;
import com.netty.demo.handler.UDPClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * UDP的连接信息主类
 * @Auther: wangjian
 * @Date: 2018-10-08 17:04:55
 */
public class UDPClient {

    public void run() {
        // 1、创建一个进行数据交互的线程处理类
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap(); // 创建客户端处理
            bootstrap.group(eventLoopGroup); // 设置连接池类型
            bootstrap.channel(NioDatagramChannel.class); // 设置Udp类型
            bootstrap.handler(new UDPClientHandler());
            bootstrap.option(ChannelOption.SO_BROADCAST, true);
            bootstrap.bind(ServerInfo.PORT).sync().channel().closeFuture().await();
        } catch (Exception e) {
            eventLoopGroup.shutdownGracefully(); // 关闭线程池
            e.printStackTrace();
        }
    }
}
