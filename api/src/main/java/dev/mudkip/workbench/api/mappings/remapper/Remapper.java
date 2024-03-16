package dev.mudkip.workbench.api.mappings.remapper;

import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.nio.file.Path;

public enum Remapper implements RemapperService {
    TINY_REMAPPER {
        @Override
        public Path remap(Path input, MemoryMappingTree mappings) {
            return null;
        }
    };
}
