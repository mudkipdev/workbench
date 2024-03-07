package dev.mudkip.workbench.api;

public interface IVersionProvider {
    String getManifestURL();
    VersionData getVersion(String name);
    String getAllVersions();
}
