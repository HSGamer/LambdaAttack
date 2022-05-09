package com.github.games647.lambdaattack.version.v1_16;

import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.bot.EntitiyLocation;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import net.kyori.adventure.text.Component;

import java.util.logging.Level;

public class SessionListener116 extends SessionAdapter {
    private final AbstractBot owner;

    public SessionListener116(AbstractBot owner) {
        this.owner = owner;
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        owner.onDisconnect(event.getReason(), event.getCause());
    }

    @Override
    public void packetReceived(PacketReceivedEvent receiveEvent) {
        if (receiveEvent.getPacket() instanceof ServerChatPacket) {
            ServerChatPacket chatPacket = receiveEvent.getPacket();
            // Message API was replaced in version 1.16
            Component message = chatPacket.getMessage();
            owner.getLogger().log(Level.INFO, "Received Message: {0}", message);
        } else if (receiveEvent.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            ServerPlayerPositionRotationPacket posPacket = receiveEvent.getPacket();

            double posX = posPacket.getX();
            double posY = posPacket.getY();
            double posZ = posPacket.getZ();
            float pitch = posPacket.getPitch();
            float yaw = posPacket.getYaw();
            EntitiyLocation location = new EntitiyLocation(posX, posY, posZ, pitch, yaw);
            owner.setLocation(location);
        } else if (receiveEvent.getPacket() instanceof ServerPlayerHealthPacket) {
            ServerPlayerHealthPacket healthPacket = receiveEvent.getPacket();
            owner.setHealth(healthPacket.getHealth());
            owner.setFood(healthPacket.getFood());
        } else if (receiveEvent.getPacket() instanceof ServerJoinGamePacket) {
            owner.onJoin();
        }
    }
}