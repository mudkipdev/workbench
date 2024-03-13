package dev.mudkip.workbench.api.utility;

public record MavenDependency(String group, String artifact, Versioning versioning) {
    public record Versioning(String latest, String release, String[] releases) {

    }
}
