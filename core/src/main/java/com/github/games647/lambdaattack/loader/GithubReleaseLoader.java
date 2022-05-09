package com.github.games647.lambdaattack.loader;

import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.bot.BotCreator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class GithubReleaseLoader implements VersionLoader {
    private final String url;

    public GithubReleaseLoader(String fileName) {
        this.url = String.format("https://github.com/HSGamer/LambdaAttack/releases/download/%s/%s", LambdaAttack.VERSION, fileName);
    }

    @Override
    public CompletableFuture<BotCreator> load() {
        return CompletableFuture.supplyAsync(() -> {
            File file;
            try {
                URLConnection connection = new URL(this.url).openConnection();
                InputStream inputStream = connection.getInputStream();
                Path temp = Files.createTempFile("lambdaattack-bot", ".jar");
                Files.copy(inputStream, temp, StandardCopyOption.REPLACE_EXISTING);
                file = temp.toFile();
                file.deleteOnExit();
            } catch (Exception e) {
                LambdaAttack.getLogger().log(Level.SEVERE, "Failed to download the file", e);
                return null;
            }
            return LoaderUtil.getBotCreator(file);
        });
    }
}
