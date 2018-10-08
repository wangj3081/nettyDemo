package com.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * 主要实现发送消息类
 * Created by wangjian on 2018/9/29.
 */
public class EchoServerHandler extends ChannelHandlerAdapter {


    /**
     * 当客户端连接到服务端之后，需要有一个连接激活的方法，此方法可以直接向客户端发送消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 1、对于发送的消息可以发送中文或者是一些标记(OK标记)
        byte[] data = "【服务端】连接通道已经建立成功，可以开始进行服务器通信处理".getBytes();
        // 2、Nio的处理本质是需要进行缓冲区的处理
        ByteBuf message = Unpooled.buffer(data.length);
        // 3、将数据写入到缓冲区之中
        message.writeBytes(data); // 写入数据
        // 4、进行信息的发送，发送完成后更新缓冲区
        ChannelFuture future = ctx.writeAndFlush(message);// 消息发送

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) { // 操作成功
                    System.out.println("【**********************服务端回应客户端成功】");
                }
            }
        });
    }

    /**
     * 当接收到消息之后会自动调用此方法，随后对消息内容进行处理
     * @param ctx
     * @param msg 接收到的消息，这个消息可能是各种类型的数据，本程序中使用的是ByteBuf
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg; // 1、接收消息内容
        String inputStr = in.toString(CharsetUtil.UTF_8); // 2、得到用户发送的数据
        String echoConntent = "【ECHO】" + inputStr;  // 3、回应的消息内容
        if ("exit".equalsIgnoreCase(inputStr)) { // 表示发送结束
            echoConntent = "quit"; // 结束的字符信息
        } else if (inputStr.startsWith("userid")) {
            echoConntent = "【服务器端】欢迎" + inputStr.split(":")[1] + "登录访问，连接通道已经建立成功，可以开始进行服务器通信处理";
        }
        ByteBuf ecbuffer = Unpooled.buffer(echoConntent.length());
        ecbuffer.writeBytes(echoConntent.getBytes());
        ctx.writeAndFlush(ecbuffer);
    }
}
