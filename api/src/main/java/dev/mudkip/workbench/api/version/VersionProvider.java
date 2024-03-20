package dev.mudkip.workbench.api.version;

public interface VersionProvider {

    /**
     * @return URL to the manifest file used by this provider
     */
    String getManifestURL();

    VersionData getVersion(String name);

    /**
     * @return All available versions which can be resolved by {@link VersionProvider#getVersion(String)}
     */
    String[] getAllVersions();
}
