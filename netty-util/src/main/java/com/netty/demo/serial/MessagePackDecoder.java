package com.netty.demo.serial;

import com.netty.demo.vo.Member;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 定义解码器、继承Netty的类操作
 * Created by wangjian on 2019/1/27.
 */
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 所有数据都在byteBuf这个变量里
        int len = byteBuf.readableBytes();
        byte[] data = new byte[len]; // 因为msgPack编码得到的是一个字节数组，定义一个与其大小一致的字节数组
        byteBuf.getBytes(byteBuf.readerIndex(), data, 0, len); // 读取数据到定义的字节数组中

        MessagePack pack = new MessagePack();
        // 在out里面注册所有要使用的读取处理操作，该操作如果直接写则意味着Handler类中接收的将是MessagePack对象
        out.add(pack.read(data, pack.lookup(Member.class)));
    }
}
