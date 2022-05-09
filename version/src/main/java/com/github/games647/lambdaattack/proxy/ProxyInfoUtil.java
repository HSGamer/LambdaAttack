package com.github.games647.lambdaattack.proxy;

import com.github.steveice10.packetlib.ProxyInfo;

import java.net.Proxy;

public class ProxyInfoUtil {
    public static ProxyInfo toProxyInfo(Proxy proxy) {
        switch (proxy.type()) {
            case SOCKS:
                return new ProxyInfo(ProxyInfo.Type.SOCKS5, proxy.address());
            case HTTP:
                return new ProxyInfo(ProxyInfo.Type.HTTP, proxy.address());
            default:
                return null;
        }
    }
}
