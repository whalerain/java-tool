package com.github.whalerain.javatool.netty;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXi
 */
class SocketServerTest {

    @Test
    void testStart() throws Exception {
        SocketServer server1 = new SocketServer(10002, new WebSocketServerInitializer(10002, "/"));
        server1.start();
        Assertions.assertTrue(NettyTool.isLocalPortUsing(10002));
        TimeUnit.SECONDS.sleep(10);
        server1.sendDataToAll(new TextWebSocketFrame("notify all client"));
        TimeUnit.SECONDS.sleep(500);
        server1.shutdown();
        TimeUnit.SECONDS.sleep(5);
        Assertions.assertFalse(NettyTool.isLocalPortUsing(10002));
    }

    @Test
    void testByteServer() throws Exception {
        SocketServer server = new SocketServer(20001, new ByteSocketServerInitializer());
        server.start();
        Assertions.assertTrue(NettyTool.isLocalPortUsing(20001));
        TimeUnit.SECONDS.sleep(500);
        server.shutdown();
    }




}
