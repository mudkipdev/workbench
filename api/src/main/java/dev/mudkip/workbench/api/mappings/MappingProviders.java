package dev.mudkip.workbench.api.mappings;

import dev.mudkip.workbench.api.utils.Utils;
import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public enum MappingProviders implements IMappingProvider {
    // OFFICIAL(false, MappingFormat.PROGUARD_FILE),
    // YARN(true, MappingFormat.TINY_FILE, MappingFormat.TINY_2_FILE),
    // MCP(true, MappingFormat.SRG_FILE),
    FEATHER(true, MappingFormat.TINY_2_FILE) {
        @Override
        public MemoryMappingTree getMappings(String version, Path file) {
            Utils.download("https://maven.ornithemc.net/#/releases/net/ornithemc/feather/" + version + "/" + version + "-mergedv2.jar", file);
            try (JarFile jarFile = new JarFile(file.toFile())) {
                JarEntry entry = jarFile.getJarEntry("mappings/mappings.tiny");
                try (InputStream stream = jarFile.getInputStream(entry)) {
                    MemoryMappingTree mem = new MemoryMappingTree();
                    MappingReader.read(new InputStreamReader(stream), mem);
                    return mem;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private final boolean hasIntermediary;
    private final MappingFormat[] formats;

    MappingProviders(boolean hasIntermediary, MappingFormat... formats) {
        this.hasIntermediary = hasIntermediary;
        this.formats = formats;
    }

    @Override
    public MappingFormat[] getSupportedFormats() {
        return formats;
    }

    @Override
    public boolean hasIntermediary() {
        return hasIntermediary;
    }
}
