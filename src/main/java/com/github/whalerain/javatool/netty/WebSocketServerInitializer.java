package com.github.whalerain.javatool.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WEB SOCKET SERVER 初始化器
 *
 * @author ZhangXi
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 服务端口
     */
    private final int port;
    /**
     * 分发路径
     */
    private final String path;

    public WebSocketServerInitializer(int port, String path) {
        this.port = port;
        this.path = path == null ? "/" : path;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket基于http协议，所以要有http编解码器，服务端用HttpServerCodec
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 聚合http片段，组合成完整的http请求数据
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        // 处理握手，心跳等杂事
        pipeline.addLast(new WebSocketServerProtocolHandler(this.path));

        // 自定义处理器
        TextMessageServerHandler handler = new TextMessageServerHandler();
        pipeline.addLast(handler);

        // 处理广播
        SocketServer.addBroadcast(port, handler);
    }
}
