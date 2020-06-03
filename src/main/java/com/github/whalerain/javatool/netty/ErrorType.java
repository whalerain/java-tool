package com.github.whalerain.javatool.netty;

/**
 * 
 *
 * @author ZhangXi
 */
public enum ErrorType {
    /**
     *
     */
    START_INTERRUPTED(1, "启动时被打断"),
    PORT_IN_USED(2, "端口被占用");


    private int code;
    private String desc;

    ErrorType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
