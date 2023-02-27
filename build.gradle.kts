plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "de.chojo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://eldonexus.de/repository/maven-proxies/")
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit", "worldedit-bukkit", "7.2.13")
}

tasks {
    register<Copy>("copyToServer") {
        val path = project.property("targetDir") ?: ""
        if (path.toString().isEmpty()) {
            println("targetDir is not set in gradle properties")
            return@register
        }
        from(jar)
        destinationDir = File(path.toString())
    }
}

bukkit {
    name = "WE-Test"
    main = "de.chojo.wetest.Main"
    commands {
        register("wetest")
    }
    depend = listOf("WorldEdit")
}
