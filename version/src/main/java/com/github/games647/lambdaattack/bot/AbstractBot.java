package com.github.games647.lambdaattack.bot;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.profile.Profile;

import java.net.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBot {
    public static final char COMMAND_IDENTIFIER = '/';
    private static final String DEFAULT_PASSWORD = "LambdaAttack";
    private final BotOptions botOptions;
    private final Proxy proxy;
    private final Profile profile;
    private final Logger logger;
    private EntitiyLocation location;
    private float health = -1;
    private float food = -1;

    protected AbstractBot(BotOptions botOptions, Profile profile, Proxy proxy) {
        this.botOptions = botOptions;
        this.proxy = proxy;
        this.profile = profile;
        this.logger = Logger.getLogger(profile.name);
    }

    public abstract void connect(String host, int port);

    public abstract void sendMessage(String message);

    public abstract boolean isOnline();

    public abstract void disconnect();

    public EntitiyLocation getLocation() {
        return location;
    }

    public void setLocation(EntitiyLocation location) {
        this.location = location;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public Logger getLogger() {
        return logger;
    }

    public Profile getProfile() {
        return profile;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public BotOptions getOptions() {
        return botOptions;
    }

    public void onDisconnect(String reason, Throwable cause) {
        getLogger().log(Level.INFO, "{0} disconnected: {1} ({2})", new Object[]{profile.name, reason, cause != null ? cause.getMessage() : "unknown"});
    }

    public void onJoin() {
        getLogger().log(Level.INFO, "{0} joined", profile.name);
        if (botOptions.autoRegister) {
            sendMessage(COMMAND_IDENTIFIER + "register " + DEFAULT_PASSWORD + ' ' + DEFAULT_PASSWORD);
            sendMessage(COMMAND_IDENTIFIER + "login " + DEFAULT_PASSWORD);
        }
    }
}
