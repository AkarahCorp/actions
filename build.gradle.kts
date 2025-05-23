plugins {
    java
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("maven-publish")
}

group = "dev.akarah"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://libraries.minecraft.net")
    }
    maven {
        url = uri("https://jitpack.io")
    }
}

val pluginPacksVer = "1af49d5b0b"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.mojang:datafixerupper:8.0.16")
    compileOnly("com.github.AkarahCorp:plugin-packs:${pluginPacksVer}")
}

tasks {
    runServer {
        minecraftVersion("1.21.4")

        downloadPlugins {
            url("https://jitpack.io/com/github/AkarahCorp/plugin-packs/${pluginPacksVer}/plugin-packs-${pluginPacksVer}.jar")
        }
    }
}

val targetJavaVersion = 21

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.named<ProcessResources>("processResources") {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.akarah"
            artifactId = "actions"
            version = "1.0-SNAPSHOT"

            from(components["java"])
        }
    }
}
