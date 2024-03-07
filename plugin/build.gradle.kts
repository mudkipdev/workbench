import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `java-gradle-plugin`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val jarInclude by configurations.creating {}

configurations.compileOnly.configure {
    extendsFrom(jarInclude)
}

tasks.withType<ShadowJar> {
    archiveClassifier = null
    configurations = listOf(jarInclude)
}

dependencies {
    implementation(":api")
}

gradlePlugin {
    plugins {
        create("workbench") {
            id = "dev.mudkip.workbench"
            displayName = "Workbench"
            description = "Gradle plugin for Minecraft"
            implementationClass = "dev.mudkip.workbench.WorkbenchPlugin"
            tags = listOf("minecraft", "modding", "decompilation", "deobfuscation", "amber", "amber-toolchain")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = groupId
            artifactId = "workbench-plugin"
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
        // Add gradle plugin portal later here
    }
}