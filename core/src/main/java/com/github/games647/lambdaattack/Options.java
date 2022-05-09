package com.github.games647.lambdaattack;

public class Options {
    public final GameVersion gameVersion;
    public final BotOptions botOptions;

    public Options(GameVersion gameVersion, BotOptions botOptions) {
        this.gameVersion = gameVersion;
        this.botOptions = botOptions;
    }
}
