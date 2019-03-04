package com.netty.demo.handler;

import com.netty.demo.vo.Member;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 使用JDK原生自带的序列化
 * 数据序列化之——对象流处理
 * Created by wangjian on 2018/10/20.
 */
public class ObjectServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Member member = (Member) msg; // 直接接收到Member对象
        System.err.println("{服务器}: " + member.toString());
        member.setName("【Echo】" + member.getName());
        member.setSalary(2 * member.getSalary());
        member.setAge(member.getAge() * 2);
        ctx.writeAndFlush(member); // 返回
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
