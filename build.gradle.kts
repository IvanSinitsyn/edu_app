plugins {
    id("java")
}

group = "ru.hoff"
version = "1.0-SNAPSHOT"

object Version {
    const val lombokVersion = "1.18.36"
    const val slf4jVersion = "2.0.6"
    const val logbackVersion = "1.4.11"
    const val junitVersion = "5.10.0"
    const val mockitoVersion = "5.8.0"
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:${Version.slf4jVersion}")
    implementation("ch.qos.logback:logback-classic:${Version.logbackVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
    implementation("org.telegram:telegrambots:6.7.0")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.1.0")
    implementation("org.telegram:telegrambots-abilities:6.0.1")
    implementation("org.telegram:telegrambots-meta:6.5.0")
    implementation("org.yaml:snakeyaml:2.0")

    compileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")

    testCompileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")
    testImplementation(platform("org.junit:junit-bom:${Version.junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.junitVersion}")
    testImplementation("org.mockito:mockito-core:${Version.mockitoVersion}")
}

tasks.test {
    useJUnitPlatform()
}