package dev.mudkip.workbench.api.utility;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
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

    public static BufferedReader readStandardTinyV2JarAsReader(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            JarEntry entry = jarFile.getJarEntry("mappings/mappings.tiny");

            return new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readStandardTinyV2JarAsReader(File file, Consumer<BufferedReader> reader) {
        try (JarFile jarFile = new JarFile(file)) {
            JarEntry entry = jarFile.getJarEntry("mappings/mappings.tiny");
            reader.accept(new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedReader newBufferedReader(Path path) {
        try {
            return Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
