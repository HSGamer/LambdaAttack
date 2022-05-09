package com.github.games647.lambdaattack;

public class BotOptions {

    public final String hostname;
    public final int port;
    public final int amount;
    public final int joinDelayMs;
    public final String botNameFormat;
    public final boolean autoRegister;

    public BotOptions(String hostname, int port, int amount, int joinDelayMs,
                      String botNameFormat, boolean autoRegister) {
        this.hostname = hostname;
        this.port = port;
        this.amount = amount;
        this.joinDelayMs = joinDelayMs;
        this.botNameFormat = botNameFormat;
        this.autoRegister = autoRegister;
    }
}
