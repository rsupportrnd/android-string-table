import com.rsupport.plugin.StringTableExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "example.app"
        minSdk = 16
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    val kotlinVersion = rootProject.extra["kotlin_version"] as String
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

apply(plugin = "android-string-table")
configure<StringTableExtension> {
    googleDriveCredentialPath.set("${project.rootDir}/app/i18n/credentials.json")
    targetSheetUrl.set("https://docs.google.com/spreadsheets/d/12hmQ7U0npYM4hK4ck3qN9bMUDRu-ZcPueluxz5X4w30/edit#gid=1661361434")
    outputXlsxFilePath.set("${project.rootDir}/app/i18n/languages.xlsx")
    rowPositionColumnHeader.set(1)
    doNotConvertNewLine.set(false)
    defaultLanguageForValues.set("en")
    androidResourcePath.set("${project.rootDir}/app/src/main/res")
    outputXmlFileName.set("strings_generated")
}
