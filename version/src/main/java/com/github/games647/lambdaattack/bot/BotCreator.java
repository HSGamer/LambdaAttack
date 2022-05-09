package com.github.games647.lambdaattack.bot;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.profile.Profile;

import java.net.Proxy;

public interface BotCreator {
    AbstractBot createBot(BotOptions botOptions, Profile profile, Proxy proxy);

    default AbstractBot createBot(BotOptions botOptions, Profile profile) {
        return createBot(botOptions, profile, Proxy.NO_PROXY);
    }
}
