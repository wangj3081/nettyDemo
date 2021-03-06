package com.netty.demo.server;

import com.netty.demo.api.ServerInfo;
import com.netty.demo.handler.ObjectServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * 流程设置服务
 * Created by wangjian on 2018/10/20.
 */
public class ObjectServer {

    public void run() throws Exception{
        // 1、在Netty里面服务器端的程序需要准备出两个线程池
        // 1.1、接收请求线程池
        EventLoopGroup boosLoopGroup = new NioEventLoopGroup();
        // 1.2、处理请求线程池
        EventLoopGroup handleLoopGroup = new NioEventLoopGroup();
        try {
            // 2、所有的服务需要通过ServerBootstrap启动
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 3、为此配置线程池(一个连接线程池、一个工作线程池)
            serverBootstrap.group(boosLoopGroup, handleLoopGroup);
            // 4、指明当前服务器运行形式
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 5、进行Netty的数据处理过滤器配置（责任链设计模式)
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 设置每行数据读取的最大行数
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536,0,3,0,3));
                    // 添加解码器对字符进行转码与解码
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    ch.pipeline().addLast(new LengthFieldPrepender(3)); // 与对象的属性个数相同
                    ch.pipeline().addLast(new ObjectEncoder()); // 解码
                    // 自定义的程序处理逻辑
                    ch.pipeline().addLast(new ObjectServerHandler());
                }
            });
            // 6、当前使用的是TCP协议、所以必须做一些TCP协议的配置
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 4); // 当处理线程全满时，最大请求等待队列长度
            // 7、绑定服务端口并且进行服务启动
            ChannelFuture future = serverBootstrap.bind(ServerInfo.PORT).sync();// 异步线程处理
            future.channel().closeFuture().sync(); /// 处理完成之后进行关闭
        } catch (Exception e) {
            boosLoopGroup.shutdownGracefully(); // 关闭主线程池
            handleLoopGroup.shutdownGracefully(); // 关闭子线程池
            e.printStackTrace();
        }

    }
}
