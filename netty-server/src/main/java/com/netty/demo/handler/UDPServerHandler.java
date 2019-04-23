package com.netty.demo.handler;

import com.netty.demo.api.ServerInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * UDP数据服务处理
 * @Auther: wangjian
 * @Date: 2018-10-08 19:20:35
 */
public class UDPServerHandler  extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String str = "Netty UDP Send Message";
        ByteBuf sendMsg = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
        // 255.255.255.255表示广播地址（向全网段的主机进行消息的发送）
        InetSocketAddress socketAddress = new InetSocketAddress("255.255.255.255", ServerInfo.PORT);
        // 创建一个UDP对应的信息数据包
        DatagramPacket packet = new DatagramPacket(sendMsg, socketAddress);
        ctx.writeAndFlush(packet).sync(); // 全部进行发送

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("【服务端-生命周期】出现异常: " + cause.getMessage());
    }
}
