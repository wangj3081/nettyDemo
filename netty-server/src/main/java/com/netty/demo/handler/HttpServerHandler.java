package com.netty.demo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义 Http 服务器
 * Http 服务响应处理
 * @Auther: wangjian
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) { // 接收到的是 Http 请求
            HttpRequest request = (HttpRequest) msg; // 处理请求
            System.out.println("【接收到的Http请求】uri: " + request.uri() +"、请求方式:" + request.method().toString());
            String resultHtml = "<!DOCTYPE html><html><head><title></title></head><body><p style=\"color:red\">红色的</p></body></html>";
            ctx.writeAndFlush(resultHtml); // 返回一段 HTML 代码
            this.responseWrite(ctx, resultHtml); // 返回内容
        }

    }

    /**
     * 设置返回内容
     * @param ctx
     * @param resultHtml
     */
    private void responseWrite(ChannelHandlerContext ctx, String resultHtml) {
        // 在 Netty 里面如果要进行传输处理则需要依靠 ByteBuf 来完成
        ByteBuf buf = Unpooled.copiedBuffer(resultHtml, CharsetUtil.UTF_8);
        // 由于实现的是一个 Http 协议，所以响应时还需要设置对应的响应头信息
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        // 设置响应的 MINE 类型
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
        // 设置响应长度
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); // 返回
    }

    // 异常信息处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
