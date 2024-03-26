package dev.mudkip.workbench.extensions;

import dev.mudkip.workbench.api.decompiler.Decompiler;
import dev.mudkip.workbench.api.mappings.MappingProvider;
import dev.mudkip.workbench.api.mappings.Mappings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;

import java.util.Arrays;
import java.util.Map;

public abstract class WorkbenchExtension implements ExtensionAware {

    public abstract Property<String> getVersion();
    public abstract Property<MappingInstance> getMappings();
    public abstract Property<DecompilerInstance> getDecompiler();

    public MappingInstance feather() {
        String[] versions = Arrays.stream(Mappings.FEATHER.getVersions())
                .filter(version -> version.contains(getVersion().get()))
                .toArray(String[]::new);
        return new MappingInstance(Mappings.FEATHER, versions[versions.length - 1]);
    }

    public MappingInstance feather(String version) {
        return new MappingInstance(Mappings.FEATHER, version);
    }

    public DecompilerInstance vineflower() {
        return new DecompilerInstance(Decompiler.VINEFLOWER, Map.of());
    }

    public DecompilerInstance vineflower(Map<String, Object> options) {
        return new DecompilerInstance(Decompiler.VINEFLOWER, options);
    }

    public record MappingInstance(MappingProvider provider, String version) {
    }

    public record DecompilerInstance(Decompiler decompiler, Map<String, Object> options) {
    }

    public static class SourcesExtension {
        // TODO: Implement
    }
}
