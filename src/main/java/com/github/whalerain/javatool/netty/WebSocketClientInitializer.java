package com.github.whalerain.javatool.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.URI;

/**
 * @author ZhangXi
 */
public class WebSocketClientInitializer extends ChannelInitializer<SocketChannel> {

    private final URI uri;

    public WebSocketClientInitializer(URI uri) {
        this.uri = uri;
    }


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket基于http协议，所以要有http编解码器，客户端使用HttpClientCodec
        pipeline.addLast(new HttpClientCodec());
        // 对写大数据流的支持
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
        // 聚合http片段，组合成完整的http请求数据
        pipeline.addLast(new ChunkedWriteHandler());
        // 处理握手，心跳等杂事
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()
        );
        pipeline.addLast(new WebSocketClientProtocolHandler(handshaker));

        // 自定义消息处理器
        pipeline.addLast(new TextMessageClientHandler(null));


    }
}
