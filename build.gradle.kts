plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.2.1"

}

group = "com.zzk"
version = "1.4.4"

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public")
    }
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies{
    implementation("com.alibaba:fastjson:1.2.83")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    intellijPlatform {
        intellijIdeaCommunity("2024.3.2")
        bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {

}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html

java {
    sourceCompatibility = JavaVersion.VERSION_17
}


tasks {

    patchPluginXml {
        sinceBuild.set("243")
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