plugins {
    java
}

group = "com.kafka.study"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:4.4.0")
}
