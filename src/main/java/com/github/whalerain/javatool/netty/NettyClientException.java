package com.github.whalerain.javatool.netty;

import lombok.Data;

/**
 * Netty客户端异常
 *
 * @author ZhangXi
 */
@Data
public class NettyClientException extends Exception {

    private ErrorType errorType;

    public NettyClientException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public NettyClientException(String message, Throwable cause, ErrorType errorType) {
        super(message, cause);
        this.errorType = errorType;
    }

    public NettyClientException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }

}
