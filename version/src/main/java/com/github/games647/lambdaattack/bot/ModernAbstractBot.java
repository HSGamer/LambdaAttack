package com.github.games647.lambdaattack.bot;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.profile.Profile;

import java.net.Proxy;

public abstract class ModernAbstractBot extends AbstractBot {
    protected ModernAbstractBot(BotOptions botOptions, Profile profile, Proxy proxy) {
        super(botOptions, profile, proxy);
    }

    @Override
    public void sendMessage(String message) {
        if (message.charAt(0) == COMMAND_IDENTIFIER) {
            sendCommand(message.substring(1));
        } else {
            sendChatMessage(message);
        }
    }

    protected abstract void sendChatMessage(String message);

    protected abstract void sendCommand(String command);
}
