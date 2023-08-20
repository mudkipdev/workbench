package dev.mudkip.workbench.gradle;

import dev.mudkip.workbench.Workbench;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public final class GradlePlugin implements Workbench, Plugin<Project> {
	@Override
	public void apply(Project project) {
		System.out.println("Hello world!");
	}
}
