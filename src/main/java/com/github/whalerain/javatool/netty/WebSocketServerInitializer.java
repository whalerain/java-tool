package com.github.whalerain.javatool.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WEB SOCKET SERVER 初始化器
 *
 * @author ZhangXi
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final int port;

    public WebSocketServerInitializer(int port) {
        this.port = port;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket 基于http协议，所以要有http编解码器 服务端用HttpServerCodec
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 聚合http片段，组合成完整的http请求数据
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 处理握手，心跳等杂事
        pipeline.addLast(new WebSocketServerProtocolHandler("/"));

        // 自定义处理器
        TextMessageHandler handler = new TextMessageHandler();
        pipeline.addLast(handler);

        // 处理广播
        SocketServer.addBroadcast(port, handler);
    }
}
