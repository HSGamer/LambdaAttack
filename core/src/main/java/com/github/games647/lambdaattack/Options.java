package com.github.games647.lambdaattack;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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

    private static final char[] allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_".toCharArray();

    public String getBotName(int botId) {
        if (botNameFormat.equalsIgnoreCase("random")) {
            return IntStream.range(0, ThreadLocalRandom.current().nextInt(3, 16))
                    .mapToObj(i -> allowedChars[ThreadLocalRandom.current().nextInt(allowedChars.length)])
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
        }
        return String.format(botNameFormat, botId);
    }
}
