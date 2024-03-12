package dev.mudkip.workbench.api;

import java.util.Arrays;

public enum GameType {
    OLD_ALPHA,
    OLD_BETA,
    SNAPSHOT,
    RELEASE;

    public static GameType get(String name) {
         return Arrays.stream(values()).filter(i -> i.toString().toLowerCase().equals(name))
                 .findFirst().orElse(null);
    }
}
