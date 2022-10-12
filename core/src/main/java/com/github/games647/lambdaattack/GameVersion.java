package com.github.games647.lambdaattack;

import com.github.games647.lambdaattack.loader.CustomLoader;
import com.github.games647.lambdaattack.loader.GithubReleaseLoader;
import com.github.games647.lambdaattack.loader.VersionLoader;

public enum GameVersion {

    VERSION_1_11("1.11", new GithubReleaseLoader("lambdaattack-version-1-11.jar")),
    VERSION_1_12("1.12.2", new GithubReleaseLoader("lambdaattack-version-1-12.jar")),
    VERSION_1_14("1.14.4", new GithubReleaseLoader("lambdaattack-version-1-14.jar")),
    VERSION_1_15("1.15.2", new GithubReleaseLoader("lambdaattack-version-1-15.jar")),
    VERSION_1_16("1.16.5", new GithubReleaseLoader("lambdaattack-version-1-16.jar")),
    VERSION_1_18("1.18.2", new GithubReleaseLoader("lambdaattack-version-1-18.jar")),
    VERSION_1_19("1.19", new GithubReleaseLoader("lambdaattack-version-1-19.jar")),
    VERSION_1_19_1("1.19.1/2", new GithubReleaseLoader("lambdaattack-version-1-19-1.jar")),
    CUSTOM("custom", new CustomLoader());

    private final String version;
    private final VersionLoader loader;

    GameVersion(String version, VersionLoader loader) {
        this.version = version;
        this.loader = loader;
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

    public VersionLoader getLoader() {
        return loader;
    }
}
