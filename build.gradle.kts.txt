plugins {
    kotlin("jvm") version "1.9.24" apply false
    id("com.android.application") version "8.1.0" apply false
    id("org.springframework.boot") version "3.1.1" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
    kotlin("plugin.spring") version "1.9.24" apply false
    kotlin("plugin.jpa") version "1.9.24" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.register("build") {
    dependsOn(subprojects.map { it.tasks.named("build") })
}
