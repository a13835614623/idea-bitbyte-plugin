plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.zzk"
version = "1.2.6"

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public")
    }
}

dependencies{
    implementation("com.alibaba:fastjson:1.2.83")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("java"))
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
}


tasks {

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("299.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set("perm:emhhbmd6aWt1YW40NTEz.OTItNzM1Mg==.tM8YqYjQl4W9YzxNSVZQ1jglE96kzI")
    }
}