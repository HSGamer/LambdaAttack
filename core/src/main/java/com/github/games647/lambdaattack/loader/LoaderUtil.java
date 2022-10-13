package com.github.games647.lambdaattack.loader;

import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.bot.BotCreator;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;

public class LoaderUtil {
    public static BotCreator getBotCreator(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Manifest manifest = jarFile.getManifest();
            String mainClass = manifest.getMainAttributes().getValue("Main-Class");
            if (mainClass == null) {
                mainClass = "com.github.games647.lambdaattack.impl.BotCreatorImpl";
            }
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, LoaderUtil.class.getClassLoader());
            Class<?> clazz = classLoader.loadClass(mainClass);
            return (BotCreator) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            LambdaAttack.getLogger().log(Level.SEVERE, "Failed to load the file", e);
            return null;
        }
    }
}
