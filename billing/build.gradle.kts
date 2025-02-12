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
    const val fasterxmlVersion = "2.15.2"
    const val snakeyamlVersion = "2.0"
    const val mokitoInline = "2.7.21"
    const val postgresql = "42.6.0"
    const val flyway = "11.2.0"
    const val kafka = "4.2.0"
    const val caffeine = "3.1.8"
    const val jacksonCore = "2.14.1"
    const val jaxbApi = "2.3.1"
    const val jaxbRuntime = "2.3.3"
    const val mapstruct = "1.5.5.Final"
    const val testContainer = "1.20.4"
    const val junitVersion = "5.10.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mapstruct:mapstruct:${Version.mapstruct}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Version.mapstruct}")

    implementation("org.slf4j:slf4j-api:${Version.slf4jVersion}")
    implementation("ch.qos.logback:logback-classic:${Version.logbackVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Version.fasterxmlVersion}")
    implementation("org.yaml:snakeyaml:${Version.snakeyamlVersion}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-configuration-processor:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Version.springVersion}")
    implementation("org.postgresql:postgresql:${Version.postgresql}")
    implementation("org.flywaydb:flyway-core:${Version.flyway}")
    implementation("org.flywaydb:flyway-database-postgresql:${Version.flyway}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Version.springVersion}")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka:${Version.kafka}")
    implementation("org.springframework.cloud:spring-cloud-starter-config:${Version.kafka}")
    implementation("org.springframework.integration:spring-integration-java-dsl:1.2.3.RELEASE")
    implementation("org.springframework.integration:spring-integration-core:6.4.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:${Version.jaxbRuntime}")
    implementation("javax.xml.bind:jaxb-api:${Version.jaxbApi}")
    implementation("com.fasterxml.jackson.core:jackson-core:${Version.jacksonCore}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.springVersion}")
    implementation("com.github.ben-manes.caffeine:caffeine:${Version.caffeine}")
    implementation("org.springframework.boot:spring-boot-starter-cache:${Version.springVersion}")

    compileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")

    testCompileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.springVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${Version.testContainer}")
    testImplementation("org.testcontainers:postgresql:${Version.testContainer}")
    testImplementation(platform("org.junit:junit-bom:${Version.junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.junitVersion}")
}

tasks.test {
    useJUnitPlatform()
}
