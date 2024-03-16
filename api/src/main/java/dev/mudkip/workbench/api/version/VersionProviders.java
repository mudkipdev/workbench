package dev.mudkip.workbench.api.version;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.mudkip.workbench.api.utility.UrlUtility;
import dev.mudkip.workbench.api.version.serializer.BetaCraftFormatSerializer;

import java.nio.file.Files;
import java.util.Objects;

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
            JsonObject data = Objects.requireNonNull(getManifestObject().getAsJsonArray("versions").asList()
                            .stream().filter(e -> e.getAsJsonObject().getAsJsonPrimitive("id").getAsString().equals(name))
                            .findFirst().orElse(null))
                    .getAsJsonObject();
            return UrlUtility.map(data.getAsJsonPrimitive("url").getAsString(), reader -> getGson().fromJson(reader, VersionData.class));
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
        this.manifestUrl = manifestUrl;
        UrlUtility.use(manifestUrl, reader ->
                manifestObject = getGson().fromJson(reader, JsonObject.class));
    }

    public static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(VersionData.class, new BetaCraftFormatSerializer())
                .create();
    }

    @Override
    public String getManifestURL() {
        return manifestUrl;
    }

    protected JsonObject getManifestObject() {
        return manifestObject;
    }
}
