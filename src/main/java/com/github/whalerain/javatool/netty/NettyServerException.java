package com.github.whalerain.javatool.netty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Netty服务端异常
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NettyServerException extends Exception {

    private ErrorType errorType;

    public NettyServerException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public NettyServerException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }

    public NettyServerException(String message, Throwable cause, ErrorType errorType) {
        super(message, cause);
        this.errorType = errorType;
    }
}
