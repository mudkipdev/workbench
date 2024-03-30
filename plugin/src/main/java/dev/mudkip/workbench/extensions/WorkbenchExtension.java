package dev.mudkip.workbench.extensions;

import dev.mudkip.workbench.api.decompiler.Decompiler;
import dev.mudkip.workbench.api.mappings.MappingProvider;
import dev.mudkip.workbench.api.mappings.Mappings;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.SourceSet;

import java.util.Arrays;
import java.util.Map;

public abstract class WorkbenchExtension implements ExtensionAware {

    public WorkbenchExtension() {
        getExtensions().create("sources", WorkbenchExtension.SourcesExtension.class);
    }

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

    public static abstract class SourcesExtension {

        public SourcesExtension(Project project) {
            getDoSplitting().convention(true);
            // https://melix.github.io/blog/2022/01/understanding-provider-api.html
            getClient().convention(project.getExtensions().getByType(JavaPluginExtension.class)
                    .getSourceSets().named("client"));
            getServer().convention(project.getExtensions().getByType(JavaPluginExtension.class)
                    .getSourceSets().named("server"));
            getCommon().convention(project.getExtensions().getByType(JavaPluginExtension.class)
                    .getSourceSets().named("common"));
        }

        public abstract Property<NamedDomainObjectProvider<SourceSet>> getClient();
        public abstract Property<NamedDomainObjectProvider<SourceSet>> getServer();
        public abstract Property<NamedDomainObjectProvider<SourceSet>> getCommon();
        public abstract Property<Boolean> getDoSplitting();

        /**
         * Will indicate to the plugin, that no splitting(client, server, common) should be done
         *
         * @param sourceSet The source set where will the decompiled code be
         */
        public void noSplit(NamedDomainObjectProvider<SourceSet> sourceSet) {
            getDoSplitting().set(false);
            getCommon().set(sourceSet);
            getCommon().finalizeValue();
        }
    }
}
