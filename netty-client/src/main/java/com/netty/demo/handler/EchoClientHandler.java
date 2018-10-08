package com.netty.demo.handler;

import com.netty.demo.util.InputUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * 客户端数据信息处理类
 * Created by wangjian on 2018/9/29.
 */
public class EchoClientHandler extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // 客户端连接激活
        String data = "userid:pursuit"; // 当前建立连接时用户的身份信息
        ByteBuf buf = Unpooled.buffer(data.length());
        buf.writeBytes(data.getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg; // 1、接收到的消息内容
        String content = in.toString(CharsetUtil.UTF_8); // 2、接收到的数据

        if ("quit".equalsIgnoreCase(content)) {
            System.out.println("###### 本次操作结束，已退出");
//            echonContent = "【服务器端】欢迎" + inputStr.split(" : ")[1] + "登录访问，连接通道已经建立成功，可以开始进行服务器通信处理";
            ctx.close(); // 关闭连接通道，和服务器端端口
        } else {
            System.out.println("【客户端】: " + content);  // 服务器端的回应数据
            String inputStr = InputUtil.getString("请输入要发送的消息：");
            ByteBuf echoBuf = Unpooled.buffer(inputStr.length());
            echoBuf.writeBytes(inputStr.getBytes()); // 写入数据
            ChannelFuture future = ctx.writeAndFlush(echoBuf);// 发送数据
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("【**********************客户端回应服务端消息成功】");
                    }
                }
            });
        }
    }
}
