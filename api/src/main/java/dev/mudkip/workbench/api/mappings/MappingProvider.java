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

    /**
     * Returns all formats supported by the MappingProvider.
     *
     * @return Supported mapping formats
     */
    MappingFormat[] getSupportedFormats();

    /**
     * @return True if the provider has an intermediary namespace
     */
    boolean hasIntermediary();

    /**
     * Returns all versions available for the MappingProvider and can be successfully
     * resolved by {@link MappingProvider#getMappings(String, Path)}.
     *
     * @return All versions available
     */
    String[] getVersions();
}
