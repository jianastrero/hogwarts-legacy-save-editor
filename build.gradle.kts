import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.jianastrero"
version = "0.2.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation("org.xerial:sqlite-jdbc:3.40.1.0")

                implementation("com.google.code.gson:gson:2.10.1")

                implementation(project(mapOf("path" to ":gvas-tool")))
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            modules("java.sql")
            windows {
                iconFile.set(project.file("icon.ico"))
            }

            targetFormats(TargetFormat.Exe)
            packageName = "hogwarts-legacy-save-editor"
            packageVersion = project.version.toString()
        }
        buildTypes {
            release {
                proguard.isEnabled.set(false)
                proguard.obfuscate.set(false)
            }
        }
    }
}

task("createVersionTxt") {
    doLast {
        val file = File("$projectDir/src/jvmMain/resources/version.txt")
        while (!file.exists()) {
            file.createNewFile()
        }
        file.outputStream().use {
            it.write("v${project.version}".toByteArray())
            it.flush()
        }
    }
}
tasks.getByPath("compileKotlinJvm").dependsOn("createVersionTxt")
