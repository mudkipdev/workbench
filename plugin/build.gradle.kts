plugins {
    `java-library`
    `java-gradle-plugin`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val jarInclude: Configuration by configurations.creating {}

configurations.compileOnly.configure {
    extendsFrom(jarInclude)
}

dependencies {
    jarInclude(project(":api"))
    implementation(gradleApi())
}

tasks.shadowJar {
    archiveClassifier = ""
    configurations = listOf(jarInclude)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

gradlePlugin {
    plugins {
        create("workbench") {
            id = "dev.mudkip.workbench"
            displayName = "workbench"
            description = "A Gradle plugin for decompiling and modding Minecraft"
            implementationClass = "dev.mudkip.workbench.WorkbenchPlugin"

            tags = listOf(
                "minecraft",
                "modding",
                "decompilation",
                "deobfuscation",
                "amber",
                "amber-toolchain"
            )
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group.toString()
            artifactId = "workbench-plugin"
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
        // gradlePluginPortal()
    }
}