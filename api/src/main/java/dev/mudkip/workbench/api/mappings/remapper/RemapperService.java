package dev.mudkip.workbench.api.mappings.remapper;

import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.nio.file.Path;

public interface RemapperService {
    Path remap(Path input, MemoryMappingTree mappings);
}
