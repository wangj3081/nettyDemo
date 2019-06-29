package com.netty.demo.handler;

import com.netty.demo.http.HttpSession;
import com.netty.demo.server.http.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * 自定义 Http 服务器
 * Http 服务响应处理
 * @Auther: wangjian
 */
public class HttpServerHandler extends ChannelHandlerAdapter {

    private HttpRequest request;
    private HttpResponse response;
    private HttpSession session;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) { // 接收到的是 Http 请求
            this.request = (HttpRequest) msg; // 处理请求
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
        this.response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        // 设置响应的 MINE 类型
        this.response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
        // 设置响应长度
        this.response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));
        // 设置Session, Session  不存在则创建
        this.setSessionId(this.isHasSessionId());
        ctx.writeAndFlush(this.response).addListener(ChannelFutureListener.CLOSE); // 返回
    }

    /**
     * 通过请求的 Cookie 分析是否存在有指定名称的 SessionId 存在
     * @return
     */
    private boolean isHasSessionId() {
        String cookieStr = (String) this.request.headers().get("Cookie");
        if (StringUtils.isBlank(cookieStr)) {
            return false;
        }
        Set<Cookie> cookieSet = ServerCookieDecoder.decode(cookieStr);// 解析全部的 Cookie 的数据
        Iterator<Cookie> iterator = cookieSet.iterator();
        while (iterator.hasNext()) {
            Cookie cookie = iterator.next();
            if (HttpSession.SESSIONID.equals(cookie.name())) { // 存在指定名字的Cookie
                if (SessionManager.isExists(cookie.value())) { // 判断该 sessionId 是否存在
                    this.session = SessionManager.getSession(cookie.value());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 创建 Session 数据
     * @param exists
     */
    private void setSessionId(boolean exists) {
        if (!exists) {  // 此时发送的请求没有指定的 Cookie 内容
            // 根据自定义的 session 管理器创建一个新的 sessionid 的内容 ，并且利用定义的常量设置 cookie 的名称
            String encode = ServerCookieEncoder.encode(HttpSession.SESSIONID, SessionManager.createSession());
            this.response.headers().set(HttpHeaderNames.SET_COOKIE, encode); // 保存cookie 数据
        }

    }

    // 异常信息处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
