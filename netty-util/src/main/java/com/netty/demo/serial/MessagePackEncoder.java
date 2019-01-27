package com.netty.demo.serial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 创建消息编码器，继承Netty的类
 * Created by wangjian on 2019/1/27.
 */
public class MessagePackEncoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext context, Object msg, ByteBuf byteBuf) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] write = pack.write(msg); // 对象序列化
        byteBuf.writeBytes(write);
    }
}
