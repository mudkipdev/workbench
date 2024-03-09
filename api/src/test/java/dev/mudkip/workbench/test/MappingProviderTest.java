package dev.mudkip.workbench.test;

import dev.mudkip.workbench.api.mappings.MappingProviders;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

class MappingProviderTest {

    @Test
    void testFeatherMappings() {
        assertDoesNotThrow(() -> {
            String[] versions = MappingProviders.FEATHER.getVersions();
            assertNotEquals(0, versions.length);
        });
    }

    @Test
    void testMemoryMappingTree() {
        assertDoesNotThrow(() -> {
            MemoryMappingTree tree = MappingProviders.FEATHER.getMappings("b1.7.3-server+build.21", new File("./mappings.zip").toPath());
        });
    }

    @AfterAll
    static void afterAll() {
        new File("./mappings.zip").delete();
    }
}
