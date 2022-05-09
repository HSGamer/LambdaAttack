package com.github.games647.lambdaattack.bot;

import com.github.games647.lambdaattack.BotOptions;
import com.github.games647.lambdaattack.profile.Profile;
import com.github.steveice10.packetlib.Session;

import java.net.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBot {
    private static final String DEFAULT_PASSWORD = "LambdaAttack";
    public static final char COMMAND_IDENTIFIER = '/';

    private final BotOptions botOptions;
    private final Proxy proxy;
    private final Profile profile;
    private final Logger logger;

    private Session session;
    private EntitiyLocation location;
    private float health = -1;
    private float food = -1;

    protected AbstractBot(BotOptions botOptions, Profile profile, Proxy proxy) {
        this.botOptions = botOptions;
        this.proxy = proxy;
        this.profile = profile;
        this.logger = Logger.getLogger(profile.name);
    }

    public void connect(String host, int port) {
        this.session = createSession(host, port);
        session.connect();
    }

    public abstract void sendMessage(String message);

    protected abstract Session createSession(String host, int port);

    public boolean isOnline() {
        return session != null && session.isConnected();
    }

    public Session getSession() {
        return session;
    }

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

    public void disconnect() {
        if (session != null) {
            session.disconnect("Disconnect");
        }
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
