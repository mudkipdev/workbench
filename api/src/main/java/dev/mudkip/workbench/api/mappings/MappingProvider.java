package dev.mudkip.workbench.api.mappings;

import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.nio.file.Path;

public interface MappingProvider {
    /**
     * Downloads and opens mapping file for reading
     *
     * @param version Mappings version
     * @param file Target download path
     * @return Mapping tree
     */
    MemoryMappingTree getMappings(String version, Path file);

    MappingFormat[] getSupportedFormats();

    boolean hasIntermediary();

    String[] getVersions();
}
