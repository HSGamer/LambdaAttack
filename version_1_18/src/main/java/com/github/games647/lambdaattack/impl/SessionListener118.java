package com.github.games647.lambdaattack.impl;

import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.bot.EntitiyLocation;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.entity.player.ClientboundSetHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosRotPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;

import java.util.logging.Level;

public class SessionListener118 extends SessionAdapter {
    private final AbstractBot owner;

    SessionListener118(AbstractBot owner) {
        this.owner = owner;
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        owner.onDisconnect(event.getReason(), event.getCause());
    }

    @Override
    public void packetReceived(Session session, Packet packet) {
        if (packet instanceof ServerboundChatPacket) {
            ServerboundChatPacket chatPacket = (ServerboundChatPacket) packet;
            // Message API was replaced in version 1.16
            String message = chatPacket.getMessage();
            owner.getLogger().log(Level.INFO, "Received Message: {0}", message);
        } else if (packet instanceof ServerboundMovePlayerPosRotPacket) {
            ServerboundMovePlayerPosRotPacket posPacket = (ServerboundMovePlayerPosRotPacket) packet;

            double posX = posPacket.getX();
            double posY = posPacket.getY();
            double posZ = posPacket.getZ();
            float pitch = posPacket.getPitch();
            float yaw = posPacket.getYaw();
            EntitiyLocation location = new EntitiyLocation(posX, posY, posZ, pitch, yaw);
            owner.setLocation(location);
        } else if (packet instanceof ClientboundSetHealthPacket) {
            ClientboundSetHealthPacket healthPacket = (ClientboundSetHealthPacket) packet;
            owner.setHealth(healthPacket.getHealth());
            owner.setFood(healthPacket.getFood());
        } else if (packet instanceof ClientboundLoginPacket) {
            owner.onJoin();
        }
    }
}