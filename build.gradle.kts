import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.godtitan"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.panda-lang.org/releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("dev.rollczi.litecommands", "bukkit", "2.8.8")
    compileOnly("io.papermc.paper", "paper-api", "1.20.1-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        destinationDirectory.set(file(buildDir))
        archiveClassifier.set("")
        minimize()

        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_module")
        exclude("META-INF/maven/**")
    }

    build {
        dependsOn(shadowJar)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}