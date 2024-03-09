package dev.mudkip.workbench.api.versions;

public interface IVersionProvider {
    String getManifestURL();
    VersionData getVersion(String name);
    String[] getAllVersions();
}
