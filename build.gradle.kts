import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.0.21"
}

group = "com.rsupport"
version = "1.2.0.15"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.12")
    testImplementation("org.hamcrest:hamcrest-library:1.3")
    testImplementation("org.hamcrest:hamcrest-core:1.3")
    api("org.apache.poi:poi:5.4.1")
    api("commons-codec:commons-codec:1.5")
    api("org.apache.poi:poi-ooxml:5.4.1")
    api("org.apache.xmlbeans:xmlbeans:2.3.0")
    api("stax:stax-api:1.0.1")
    api("dom4j:dom4j:1.6.1")
    api("xml-apis:xml-apis:1.0.b2")
    api("org.jdom:jdom2:2.0.6.1")
    implementation("com.google.api-client:google-api-client:1.30.4")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.30.4")
    implementation("com.google.apis:google-api-services-drive:v3-rev110-1.23.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    api(gradleApi())
    api(localGroovy())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

gradlePlugin {
    plugins {
        create("stringTablePlugin") {
            id = "android-string-table"
            implementationClass = "com.rsupport.plugin.StringTablePlugin"
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}
