package com.netty.demo.handler;

import com.netty.demo.api.DefaultNettyInfo;
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

    private final int REPEAT = 500;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // 客户端连接激活
//        String data = "userid:pursuit"; // 当前建立连接时用户的身份信息
//        ByteBuf buf = Unpooled.buffer(data.length());
//        buf.writeBytes(data.getBytes());
//        ctx.writeAndFlush(data);
        String inputStr = InputUtil.getString("请输入要发送的消息：");
//        for (int i = 0; i < REPEAT; i++) {
//            ctx.writeAndFlush(inputStr + " : " + i + DefaultNettyInfo.SPLIT_SMBOL); // 发送数据
            ctx.writeAndFlush(inputStr + DefaultNettyInfo.SPLIT_SMBOL); // 发送数据
//        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String getMessage = (String) msg; // 接收数据
        if ("close".equals(getMessage)) {
            // 关闭连接
            System.out.println(ctx.channel().close().isDone());
        } else {
            System.out.println("{客户端}: " + getMessage); // 服务器返回消息
        }
    }
}
