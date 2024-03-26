package dev.mudkip.workbench;

import dev.mudkip.workbench.extensions.WorkbenchExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class WorkbenchPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project project) {
        WorkbenchExtension wbExt = project.getExtensions().create("workbench", WorkbenchExtension.class);
        wbExt.getExtensions().create("sources", WorkbenchExtension.SourcesExtension.class);
    }
}
