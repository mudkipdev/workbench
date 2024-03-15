package dev.mudkip.workbench.api.utility;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class FileUtility {
    public static MemoryMappingTree readStandardTinyV2Jar(File file) {
        try (JarFile jarFile = new JarFile(file)) {
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
}
