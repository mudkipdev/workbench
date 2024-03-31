package dev.mudkip.workbench;

import dev.mudkip.workbench.extensions.WorkbenchExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class WorkbenchPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {
        project.getPluginManager().apply(JavaPlugin.class);

        WorkbenchExtension wbExt = project.getExtensions().create("workbench", WorkbenchExtension.class, project);
    }
}
