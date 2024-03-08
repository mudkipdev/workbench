package dev.mudkip.workbench.api.mappings;

import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.nio.file.Path;

public interface IMappingProvider {
    MemoryMappingTree getMappings(String version, Path file);
    MappingFormat[] getSupportedFormats();
    boolean hasIntermediary();
}
