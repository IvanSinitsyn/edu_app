plugins {
    id("org.springframework.boot") version "3.2.11"
    id("java")
}

group = "ru.hoff"
version = "1.0-SNAPSHOT"

object Version {
    const val springVersion = "3.2.11"
    const val lombokVersion = "1.18.36"
    const val slf4jVersion = "2.0.6"
    const val logbackVersion = "1.4.11"
    const val junitVersion = "5.10.0"
    const val mockitoVersion = "5.8.0"
    const val fasterxmlVersion = "2.15.2"
    const val snakeyamlVersion = "2.0"
    const val mokitoInline = "2.7.21"
    const val postgresql = "42.6.0"
    const val flyway = "11.2.0"
    const val swagger = "2.8.3"
    const val jpa = "3.4.2"
    const val h2 = "2.3.232"
    const val springShellStarter = "3.4.0"
    const val springBootStarterActuator = "3.4.1"
    const val kafka = "3.2.1"
    const val assertj = "3.24.2"
    const val jacksonCore = "2.14.1"
    const val jaxbApi = "2.3.1"
    const val jaxbRuntime = "2.3.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    implementation("org.slf4j:slf4j-api:${Version.slf4jVersion}")
    implementation("ch.qos.logback:logback-classic:${Version.logbackVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Version.fasterxmlVersion}")
    implementation("org.yaml:snakeyaml:${Version.snakeyamlVersion}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-configuration-processor:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${Version.springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Version.springVersion}")
    implementation("org.postgresql:postgresql:${Version.postgresql}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${Version.springVersion}")
    implementation("org.flywaydb:flyway-core:${Version.flyway}")
    implementation("org.flywaydb:flyway-database-postgresql:${Version.flyway}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Version.swagger}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Version.jpa}")
    implementation("org.glassfish.jaxb:jaxb-runtime:${Version.jaxbRuntime}")
    implementation("javax.xml.bind:jaxb-api:${Version.jaxbApi}")
    implementation("com.fasterxml.jackson.core:jackson-core:${Version.jacksonCore}")
    implementation("org.springframework.shell:spring-shell-starter:${Version.springShellStarter}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${Version.springBootStarterActuator}")
    implementation("org.springframework.kafka:spring-kafka:${Version.kafka}")


    compileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")

    testCompileOnly("org.projectlombok:lombok:${Version.lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${Version.lombokVersion}")
    testImplementation(platform("org.junit:junit-bom:${Version.junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.junitVersion}")
    testImplementation("org.mockito:mockito-core:${Version.mockitoVersion}")
    testImplementation("org.mockito:mockito-inline:${Version.mokitoInline}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.springVersion}")
    testImplementation("com.h2database:h2:${Version.h2}")
    testImplementation("org.assertj:assertj-core:${Version.assertj}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    archiveFileName.set("edu_app.jar")
    manifest {
        attributes["Main-Class"] = "ru.hoff.edu.EduAppApplication"
    }
}
