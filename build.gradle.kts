import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.jianastrero"
version = "0.1.1"

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
