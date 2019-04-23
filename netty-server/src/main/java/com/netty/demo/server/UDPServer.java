package com.netty.demo.server;

import com.netty.demo.handler.UDPServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * UDP主服务类，进行数据发送配置
 * @Auther: wangjian
 * @Date: 2018-10-08 19:27:42
 */
public class UDPServer {

    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioDatagramChannel.class);
            bootstrap.handler(new UDPServerHandler());
            bootstrap.option(ChannelOption.SO_BROADCAST, true); // 允许广播
            ChannelFuture future = bootstrap.bind(0).sync(); // 线程异步处理
            future.channel().closeFuture().sync(); // 广播后关闭
        }catch (Exception e) {
            group.shutdownGracefully(); // 关闭线程池
        }
    }

}
