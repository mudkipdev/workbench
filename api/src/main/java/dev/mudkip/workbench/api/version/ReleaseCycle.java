package dev.mudkip.workbench.api.version;

import java.util.Arrays;
import java.util.Optional;

public enum ReleaseCycle {
    OLD_ALPHA,
    OLD_BETA,
    SNAPSHOT,
    RELEASE;

    public static Optional<ReleaseCycle> get(String name) {
         return Arrays.stream(values())
                 .filter(value -> value.toString().toLowerCase().equals(name))
                 .findFirst();
    }
}
