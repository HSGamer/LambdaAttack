package com.github.games647.lambdaattack.loader;

import com.github.games647.lambdaattack.bot.BotCreator;

import java.util.concurrent.CompletableFuture;

public interface VersionLoader {
    CompletableFuture<BotCreator> load();
}
