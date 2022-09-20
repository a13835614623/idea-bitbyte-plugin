import java.util.regex.Pattern.compile

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.zzk"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public")
    }
}

dependencies{
    implementation("com.alibaba:fastjson:1.2.83")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("java"))
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
}


tasks {

    patchPluginXml {
        sinceBuild.set("213")
        untilBuild.set("223.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}