package dev.mudkip.workbench.test;

import dev.mudkip.workbench.api.mappings.Mappings;
import dev.mudkip.workbench.api.mappings.remapper.Remapper;
import dev.mudkip.workbench.api.utility.FileUtility;
import dev.mudkip.workbench.api.utility.UrlUtility;
import dev.mudkip.workbench.api.version.VersionProviders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

class RemapperTest {

    static Path mappingsFile = Path.of("./mappings.jar");
    static Path jarFile = Path.of("./minecraft.jar");

    @BeforeAll
    static void setup() {
        Mappings.BABRIC_INTERMEDIARY.getMappings("b1.7.3", mappingsFile);
        UrlUtility.download(VersionProviders.BETACRAFT.getVersion("b1.7.3").getDownloads().clientUrl(), jarFile);
    }

    @Test
    void testRemapping() {
        Assertions.assertDoesNotThrow(() -> {
            FileUtility.readStandardTinyV2JarAsReader(mappingsFile.toFile(), reader -> {
                Remapper.TINY_REMAPPER.remap(jarFile, Path.of("./remapped.jar"), "client", "intermediary",
                        reader);
            });
        });
    }

    @AfterAll
    static void cleanup() {
        mappingsFile.toFile().delete();
        jarFile.toFile().delete();
        Path.of("./remapped.jar").toFile().delete();
    }
}
