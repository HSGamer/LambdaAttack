package com.github.games647.lambdaattack.version.v1_18;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.games647.lambdaattack.proxy.ProxyInfoUtil;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;

import java.net.Proxy;

public class Bot118 extends AbstractBot {
    public Bot118(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void sendMessage(String message) {
        getSession().send(new ClientboundChatPacket(message));
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

        Session session = new TcpClientSession(
                host, port,
                protocol,
                ProxyInfoUtil.toProxyInfo(getProxy())
        );
        session.addListener(new SessionListener118(this));
        return session;
    }
}
