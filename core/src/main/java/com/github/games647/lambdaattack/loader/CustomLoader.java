package com.github.games647.lambdaattack.loader;

import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.bot.BotCreator;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class CustomLoader implements VersionLoader {
    @Override
    public CompletableFuture<BotCreator> load() {
        return CompletableFuture.supplyAsync(() -> {
            File file = new File("bot.jar");
            if (!file.exists()) {
                LambdaAttack.getLogger().log(Level.SEVERE, "Failed to find the file");
                return null;
            }
            return LoaderUtil.getBotCreator(file);
        });
    }
}
