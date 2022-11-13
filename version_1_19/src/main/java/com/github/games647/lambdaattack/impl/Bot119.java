package com.github.games647.lambdaattack.impl;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.ModernAbstractBot;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

import java.net.Proxy;
import java.time.Instant;
import java.util.Collections;

public class Bot119 extends ModernAbstractBot {
    private Session session;

    Bot119(BotOptions botOptions, Profile profile, Proxy proxy) {
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
        session.addListener(new SessionListener119(this));
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
    protected void sendChatMessage(String message) {
        session.send(new ServerboundChatPacket(message, Instant.now().toEpochMilli(), 0, new byte[0], false));
    }

    @Override
    protected void sendCommand(String command) {
        session.send(new ServerboundChatCommandPacket(command, Instant.now().toEpochMilli(), 0, Collections.emptyMap(), false));
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
