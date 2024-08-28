import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

description = "message"
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
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
}
tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set("bytecar/message:v1.0")
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