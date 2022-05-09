package com.github.games647.lambdaattack;

import com.github.games647.lambdaattack.bot.BotCreator;
import com.github.games647.lambdaattack.version.v1_11.Bot111;
import com.github.games647.lambdaattack.version.v1_12.Bot112;
import com.github.games647.lambdaattack.version.v1_14.Bot114;
import com.github.games647.lambdaattack.version.v1_15.Bot115;
import com.github.games647.lambdaattack.version.v1_16.Bot116;

public enum GameVersion {

    VERSION_1_11("1.11", Bot111::new),
    VERSION_1_12("1.12.2", Bot112::new),
    VERSION_1_14("1.14.4", Bot114::new),
    VERSION_1_15("1.15.2", Bot115::new),
    VERSION_1_16("1.16.5", Bot116::new);

    private final String version;
    private final BotCreator creator;

    GameVersion(String version, BotCreator creator) {
        this.version = version;
        this.creator = creator;
    }

    public static GameVersion findByName(String name) {
        for (GameVersion version : values()) {
            if (version.version.equals(name)) {
                return version;
            }
        }

        return null;
    }

    public String getVersion() {
        return version;
    }

    public BotCreator getCreator() {
        return creator;
    }
}
