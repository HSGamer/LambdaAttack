package com.github.games647.lambdaattack.loader;

import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.bot.BotCreator;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

public class LoaderUtil {
    public static BotCreator getBotCreator(File file) {
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, LoaderUtil.class.getClassLoader());
            Class<?> clazz = classLoader.loadClass("com.github.games647.lambdaattack.impl.BotCreatorImpl");
            return (BotCreator) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LambdaAttack.getLogger().log(Level.SEVERE, "Failed to load the file", e);
            return null;
        }
    }
}
