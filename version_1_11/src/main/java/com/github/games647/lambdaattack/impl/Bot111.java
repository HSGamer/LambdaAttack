package com.github.games647.lambdaattack.impl;

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

public class Bot111 extends AbstractBot {
    private Session session;

    Bot111(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void connect(String host, int port) {
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
        session = client.getSession();
        session.addListener(new SessionListener111(this));
        session.connect();
    }

    @Override
    public void sendMessage(String message) {
        session.send(new ClientChatPacket(message));
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
