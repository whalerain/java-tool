package com.github.whalerain.javatool.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * SOCKET 客户端
 *
 * @author ZhangXi
 */
public class SocketClient {

    private String host;

    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(this.host, this.port))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_TIMEOUT, 10000)
                    .handler(new SocketClientInitializer());

            ChannelFuture future = bootstrap.connect().sync();

            future.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            clientGroup.shutdownGracefully();
        }
    }


    private static class SocketClientInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {

        }
    }


}
