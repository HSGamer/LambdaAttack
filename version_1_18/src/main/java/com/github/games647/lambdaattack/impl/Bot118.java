package com.github.games647.lambdaattack.impl;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

import java.net.Proxy;

public class Bot118 extends AbstractBot {
    private Session session;

    Bot118(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void connect(String host, int port) {
        MinecraftProtocol protocol;
        if (getProfile().password.isEmpty()) {
            protocol = new MinecraftProtocol(getProfile().name);
        } else {
            // TODO: Implement password
            throw new UnsupportedOperationException("Password is not supported");
        }

        session = new TcpClientSession(
                host, port,
                protocol,
                toProxyInfo(getProxy())
        );
        session.addListener(new SessionListener118(this));
        session.connect();
    }

    private ProxyInfo toProxyInfo(Proxy proxy) {
        switch (proxy.type()) {
            case SOCKS:
                return new ProxyInfo(ProxyInfo.Type.SOCKS5, proxy.address());
            case HTTP:
                return new ProxyInfo(ProxyInfo.Type.HTTP, proxy.address());
            default:
                return null;
        }
    }

    @Override
    public void sendMessage(String message) {
        session.send(new ServerboundChatPacket(message));
    }

    @Override
    public boolean isOnline() {
        return session.isConnected();
    }

    @Override
    public void disconnect() {
        session.disconnect("Disconnect");
    }
}
