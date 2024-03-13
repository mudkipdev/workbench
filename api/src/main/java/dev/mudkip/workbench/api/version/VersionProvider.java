package dev.mudkip.workbench.api.version;

public interface VersionProvider {
    String getManifestURL();

    VersionData getVersion(String name);

    String[] getAllVersions();
}
