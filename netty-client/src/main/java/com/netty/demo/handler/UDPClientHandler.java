package com.netty.demo.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * UDP客户端数据处理类
 * @Auther: wangjian
 * @Date: 2018-10-08 19:02:46
 */
public class UDPClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DatagramPacket datagramPacket = (DatagramPacket) msg; // 接收数据包
        String data = datagramPacket.content().toString(CharsetUtil.UTF_8);
        System.out.println("【客户端(监听者)】数据: " + data);
        System.out.println("【客户端(监听者)】来源: " + datagramPacket.sender());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
