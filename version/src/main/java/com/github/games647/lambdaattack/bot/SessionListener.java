package com.github.games647.lambdaattack.bot;

import com.github.games647.lambdaattack.BotOptions;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;

import java.util.logging.Level;

public abstract class SessionListener extends SessionAdapter {
    private static final String DEFAULT_PASSWORD = "LambdaAttack";

    protected final BotOptions botOptions;
    protected final AbstractBot owner;

    protected SessionListener(BotOptions botOptions, AbstractBot owner) {
        this.botOptions = botOptions;
        this.owner = owner;
    }

    @Override
    public void disconnected(DisconnectedEvent disconnectedEvent) {
        String reason = disconnectedEvent.getReason();
        owner.getLogger().log(Level.INFO, "Disconnected: {0}", reason);
    }

    public void onJoin() {
        if (botOptions.autoRegister) {
            owner.sendMessage(AbstractBot.COMMAND_IDENTIFIER + "register " + DEFAULT_PASSWORD + ' ' + DEFAULT_PASSWORD);
            owner.sendMessage(AbstractBot.COMMAND_IDENTIFIER + "login " + DEFAULT_PASSWORD);
        }
    }
}
