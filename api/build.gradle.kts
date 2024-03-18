plugins {
    `java-library`
}

repositories {
    maven("https://maven.fabricmc.net/")
}

dependencies {
    implementation("net.fabricmc:tiny-remapper:0.10.1")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("net.fabricmc:mapping-io:0.5.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.vineflower:vineflower:1.9.3")
    implementation("org.slf4j:slf4j-api:2.0.12")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}