import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

description = "cards"
version = "v1.0"
group = "com.easybytes"

plugins {
    java
    id("org.springframework.boot") version("3.3.3")
    id("io.spring.dependency-management") version("1.1.6")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        vendor = JvmVendorSpec.AMAZON
    }
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.3")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
    }
}
springBoot {
    buildInfo()
}
repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus:1.13.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-discoveryclient")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    runtimeOnly("io.opentelemetry.javaagent:opentelemetry-javaagent:2.7.0")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}
tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set("bytecar/cards:v1.0")
    publish.set(true)
    docker {
        publishRegistry {
            username.set("bytecar")
            password.set("dckr_pat_anymvfNypwc5NkxGK5BX9hPXOEE")
        }
    }
    environment.putAll(mapOf(
        "BP_JVM_VERSION" to "21",
        "BP_JVM_JLINK_ENABLED" to "true"
    ))
}