package com.netty.demo.handler;

import com.netty.demo.vo.Member;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * 使用MsgPack序列化
 * 数据序列化之——MsgPack——客户端数据信息处理类
 * Created by wangjian on 2018/9/29.
 */
public class MsgPackClientHandler extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // 客户端连接激活
        for (int i = 0; i < 100; i++) {
            Member data = new Member();
            data.setName("张三-" + i);
            data.setSalary(i * 1000.00);
            data.setAge(i);
            ctx.writeAndFlush(data); // 加了字符串解码器之后可以直接发字符串
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Member member = (Member) msg; // 接收到返回的对象数据
        System.out.println(member.toString());
        TimeUnit.MILLISECONDS.sleep(10); // 休眠10毫秒
    }
}
