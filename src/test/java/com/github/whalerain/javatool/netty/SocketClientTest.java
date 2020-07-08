package com.github.whalerain.javatool.netty;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author ZhangXi
 */
@Slf4j
class SocketClientTest {

    @Test
    void testStart() throws Exception {
        URI uri = new URI("ws://123.207.136.134:9010/ajaxchattest");
        SocketClient client = new SocketClient("123.207.136.134", 9010, new WebSocketClientInitializer(uri));
        client.startConnect();

        TimeUnit.SECONDS.sleep(5);
        log.info("准备发送数据...");
        client.sendData(new TextWebSocketFrame("wadadad"));
        TimeUnit.SECONDS.sleep(20);
        client.close();
    }

    @Test
    void testByteClient() throws Exception {
        SocketClient client = new SocketClient("localhost", 20001, new ByteSocketClientInitializer());
        client.startConnect();
        TimeUnit.SECONDS.sleep(5);
        log.info("准备发送数据...");
        client.sendData(new byte[]{1,1,1,1,1,0,1,5,1,1});
        client.sendData(new byte[]{1,9,1,1,1,0,1,1,1,1});
        client.sendData(new byte[]{1,1,1,1,1,0,99,1,1,1});
        TimeUnit.SECONDS.sleep(5);
        client.close();
    }



    void test() {

    }



}
