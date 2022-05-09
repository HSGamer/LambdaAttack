package com.github.games647.lambdaattack.impl;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.bot.AbstractBot;
import com.github.games647.lambdaattack.bot.BotCreator;
import com.github.games647.lambdaattack.profile.Profile;

import java.net.Proxy;

public class BotCreatorImpl implements BotCreator {
    @Override
    public AbstractBot createBot(BotOptions botOptions, Profile profile, Proxy proxy) {
        return new Bot118(botOptions, profile, proxy);
    }
}
