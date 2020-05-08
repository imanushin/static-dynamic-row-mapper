import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
}

repositories {
    jcenter()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.2.6.RELEASE")

    implementation(kotlin("script-runtime"))
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("script-util"))
    implementation(kotlin("scripting-compiler-embeddable"))

    testImplementation("io.kotest:kotest-runner-junit5:4.0.5")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    test {
        useJUnitPlatform()
    }
}

