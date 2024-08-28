import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

description = "gatewayserver"
version = "v1.0"
group = "com.easybytes"

plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
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
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-discoveryclient")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set("bytecar/gatewayserver:v1.0")
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
