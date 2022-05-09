package com.github.games647.lambdaattack.version.v1_16;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.bot.SessionListener;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.games647.lambdaattack.proxy.ProxyInfoUtil;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

import java.net.Proxy;

public class Bot116 extends AbstractBot {
    public Bot116(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void sendMessage(String message) {
        getSession().send(new ClientChatPacket(message));
    }

    @Override
    protected SessionListener getSessionListener() {
        return new SessionListener116(getOptions(), this);
    }

    @Override
    protected Session createSession(String host, int port) {
        MinecraftProtocol protocol;
        if (getProfile().password.isEmpty()) {
            protocol = new MinecraftProtocol(getProfile().name);
        } else {
            // TODO: Implement password
            throw new UnsupportedOperationException("Password is not supported");
        }

        return new TcpClientSession(
                host, port,
                protocol,
                ProxyInfoUtil.toProxyInfo(getProxy())
        );
    }
}
