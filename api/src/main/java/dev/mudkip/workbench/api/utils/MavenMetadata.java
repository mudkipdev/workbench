package dev.mudkip.workbench.api.utils;

public final class MavenMetadata {

    private String groupId;
    private String artifactId;
    private Versioning versioning;

    public MavenMetadata(String groupId, String artifactId, Versioning versioning) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versioning = versioning;
    }

    public MavenMetadata() {}

    public record Versioning(String latest, String release, String[] releases) {}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public Versioning getVersioning() {
        return versioning;
    }

    public void setVersioning(Versioning versioning) {
        this.versioning = versioning;
    }
}
