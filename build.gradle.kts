plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
    jar {
        manifest.attributes["Main-Class"] = "advent.of.code.MainKt"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(
            configurations.runtimeClasspath
                .get()
                .filter { it.name.endsWith(".jar") }
                .map { zipTree(it) }
        )
    }
    compileKotlin {
        kotlinOptions.suppressWarnings = true
    }
}