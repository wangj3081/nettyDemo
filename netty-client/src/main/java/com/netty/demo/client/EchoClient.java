package com.netty.demo.client;

import com.netty.demo.api.ServerInfo;
import com.netty.demo.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by wangjian on 2018/9/29.
 */
public class EchoClient {

    public void run() { // 启动客户端程序

        // 1、创建一个进行数据交互的处理线程池
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap(); // 创建客户端处理
            bootstrap.group(group); // 设置连接池
            bootstrap.channel(NioSocketChannel.class); // 设置通道类型
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoClientHandler()); // 自定义程序处理逻辑
                }
            });
            // 连接服务端
            ChannelFuture future = bootstrap.connect(ServerInfo.HOSTNAME, ServerInfo.PORT).sync();
            future.channel().closeFuture().sync(); // 等待关闭，Handler里面关闭处理
        } catch (Exception e) {
            group.shutdownGracefully(); // 关闭线程池
            e.printStackTrace();
        }
    }
}
