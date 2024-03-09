plugins {
    `java-library`
}

dependencies {
    implementation("net.fabricmc:mapping-io:0.5.1")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}