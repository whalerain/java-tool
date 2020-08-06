package com.github.whalerain.javatool.http;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestTool {


    public static String obtainRealIp(HttpServletRequest request) {
        //fixme 还有很多改进空间
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String oneIp : ips) {
                if (!"unknown".equals(oneIp)) {
                    ip = oneIp;
                    break;
                }
            }
        }
        return ip;
    }





}
