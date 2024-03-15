package dev.mudkip.workbench.test;

import dev.mudkip.workbench.api.mappings.Mappings;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;

class MappingProviderTest {

    @Test
    void testFeatherMappings() {
        assertDoesNotThrow(() -> {
            String[] versions = Mappings.FEATHER.getVersions();
            assertNotEquals(0, versions.length);
        });
    }

    @Test
    void testMemoryMappingTree() {
        assertDoesNotThrow(() -> {
            MemoryMappingTree tree = Mappings.BABRIC_INTERMEDIARY.getMappings("b1.7.3", new File("./mappings.zip").toPath());
        });
    }

    @AfterAll
    static void afterAll() {
        new File("./mappings.zip").delete();
    }
}
