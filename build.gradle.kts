plugins {
    java
}

allprojects {
    group = "dev.mudkip"
    version = "0.1.0"

    plugins.withId("java") {
        java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(17)
            }
        }

        repositories {
            mavenCentral()
        }
    }
}