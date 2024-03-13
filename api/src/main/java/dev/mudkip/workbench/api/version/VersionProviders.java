package dev.mudkip.workbench.api.version;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.mudkip.workbench.api.utility.UrlUtility;

public enum VersionProviders implements VersionProvider {
    OFFICIAL("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json") {
        @Override
        public VersionData getVersion(String name) {
            throw new UnsupportedOperationException("Unimplemented operation.");
        }

        @Override
        public String[] getAllVersions() {
            throw new UnsupportedOperationException("Unimplemented operation.");
        }
    },
    BETACRAFT("https://files.betacraft.uk/launcher/v2/assets/version_list.json") {
        @Override
        public VersionData getVersion(String name) {
            return null;
        }

        @Override
        public String[] getAllVersions() {
            return getManifestObject()
                    .getAsJsonArray("versions").asList()
                    .stream().map(e -> e.getAsJsonObject().getAsJsonPrimitive("id").getAsString())
                    .toArray(String[]::new);
        }
    };

    private final String manifestUrl;
    private JsonObject manifestObject;

    VersionProviders(String manifestUrl) {
        Gson gson = new Gson();
        this.manifestUrl = manifestUrl;

        UrlUtility.use(manifestUrl, reader ->
                manifestObject = gson.fromJson(reader, JsonObject.class));
    }

    @Override
    public String getManifestURL() {
        return manifestUrl;
    }

    protected JsonObject getManifestObject() {
        return manifestObject;
    }
}
