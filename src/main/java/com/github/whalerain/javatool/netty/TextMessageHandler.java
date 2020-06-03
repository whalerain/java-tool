package com.github.whalerain.javatool.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文本消息数据处理器
 *
 * @author ZhangXi
 */
@Slf4j
public class TextMessageHandler extends ChannelInboundHandlerAdapter implements Broadcast {

    /**
     * 客户端Channel池
     */
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 注册连接", NettyTool.getClientAddress(ctx));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 断开连接", NettyTool.getClientAddress(ctx));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 活跃", NettyTool.getClientAddress(ctx));
        this.channels.add(ctx.channel());
        log.info("客户端：{} 已加入群组", ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端：{} 不活跃", NettyTool.getClientAddress(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            String val = ((TextWebSocketFrame) msg).text();
            log.info("客户端：{} 传入数据：{}", NettyTool.getClientAddress(ctx), val);
        } else {
            log.warn("客户端：{} 传入未处理数据类型：{}", NettyTool.getClientAddress(ctx), msg.getClass().getName());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("客户端：{} 数据传输完成", NettyTool.getClientAddress(ctx));
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端：{} 数据传输出错，关闭连接", NettyTool.getClientAddress(ctx),  cause);
        ctx.close();
    }

    @Override
    public void sendAll(Object msg) {
        channels.writeAndFlush(msg);
    }

    @Override
    public void send(Object msg, ChannelId id) {
        Channel channel = channels.find(id);
        if (null != channel) {
            channel.writeAndFlush(msg);
        }
    }
}
