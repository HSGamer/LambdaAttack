package com.github.games647.lambdaattack.version.v1_12;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;

public class Bot112 extends AbstractBot {
    public Bot112(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void sendMessage(String message) {
        getSession().send(new ClientChatPacket(message));
    }

    @Override
    protected Session createSession(String host, int port) {
        MinecraftProtocol protocol;
        if (getProfile().password.isEmpty()) {
            protocol = new MinecraftProtocol(getProfile().name);
        } else {
            try {
                protocol = new MinecraftProtocol(getProfile().name, getProfile().password);
            } catch (RequestException e) {
                throw new IllegalStateException(e);
            }
        }

        Client client;
        if (getProxy() == null) {
            client = new Client(host, port, protocol, new TcpSessionFactory());
        } else {
            client = new Client(host, port, protocol, new TcpSessionFactory(getProxy()));
        }
        Session session = client.getSession();
        session.addListener(new SessionListener112(this));
        return client.getSession();
    }
}
