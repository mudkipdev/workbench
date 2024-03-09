package dev.mudkip.workbench.api.mappings;

import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.nio.file.Path;

public interface IMappingProvider {
    /**
     * Downloads and opens mapping file for reading
     *
     * @param version mapping version
     * @param file target download path
     * @return mapping tree
     */
    MemoryMappingTree getMappings(String version, Path file);
    MappingFormat[] getSupportedFormats();
    boolean hasIntermediary();
    String[] getVersions();
}
