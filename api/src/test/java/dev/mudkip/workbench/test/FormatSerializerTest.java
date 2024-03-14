package dev.mudkip.workbench.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.mudkip.workbench.api.utility.UrlUtility;
import dev.mudkip.workbench.api.version.VersionData;
import dev.mudkip.workbench.api.version.serializer.BetaCraftFormatSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FormatSerializerTest {

    @Test
    void validateObject() {
        Gson gson = new GsonBuilder().registerTypeAdapter(VersionData.class, new BetaCraftFormatSerializer()).create();
        UrlUtility.use("https://files.betacraft.uk/launcher/v2/assets/jsons/1.3.1.json", reader -> {
            Assertions.assertDoesNotThrow(() -> {
                VersionData versionData = gson.fromJson(reader, VersionData.class);
                Assertions.assertNotNull(versionData);
                Assertions.assertNotNull(versionData.getLibraries());
                Assertions.assertNotEquals(0, versionData.getLibraries().size());
            });
        });
    }
}
