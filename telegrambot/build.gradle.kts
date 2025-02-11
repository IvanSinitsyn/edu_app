plugins {
    id("java")
}

group = "ru.hoff"
version = "1.0-SNAPSHOT"

object Version {
    const val springVersion = "3.2.11"
    const val lombokVersion = "1.18.36"
    const val slf4jVersion = "2.0.6"
    const val logbackVersion = "1.4.11"
    const val telegrambotsVersion = "5.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:${Version.slf4jVersion}")
    implementation("ch.qos.logback:logback-classic:${Version.logbackVersion}")
    implementation("org.telegram:telegrambots:${Version.telegrambotsVersion}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-configuration-processor:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Version.springVersion}")

    compileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")

    testCompileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")
}

tasks.test {
    useJUnitPlatform()
}
