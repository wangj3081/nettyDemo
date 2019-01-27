package com.netty.demo.client;

import com.netty.demo.api.ServerInfo;
import com.netty.demo.handler.EchoClientHandler;
import com.netty.demo.handler.ObjectClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * TCP的信息连接处理
 * Created by wangjian on 2018/9/29.
 */
public class ObjectClient {

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
                    // 设置每行数据读取的最大行数
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 3,0,3));
                    // 添加字符串解码器进行转码和解码
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    ch.pipeline().addLast(new LengthFieldPrepender(3)); // 与解码的对象参数个数相同
                    ch.pipeline().addLast(new ObjectEncoder()); // 解码
                    // 自定义程序处理逻辑
                    ch.pipeline().addLast(new ObjectClientHandler());
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
