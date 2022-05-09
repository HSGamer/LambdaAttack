package com.github.games647.lambdaattack;

public class Options {
    public final GameVersion gameVersion;

    public final String hostname;
    public final int port;
    public final int amount;
    public final int joinDelayMs;
    public final String botNameFormat;
    public final BotOptions botOptions;

    public Options(GameVersion gameVersion, String hostname, int port, int amount, int joinDelayMs, String botNameFormat, BotOptions botOptions) {
        this.gameVersion = gameVersion;
        this.hostname = hostname;
        this.port = port;
        this.amount = amount;
        this.joinDelayMs = joinDelayMs;
        this.botNameFormat = botNameFormat;
        this.botOptions = botOptions;
    }
}
