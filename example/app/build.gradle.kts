plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("android-string-table")
}

android {
    namespace = "example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "example.app"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

androidStringTable {
    googleDriveCredentialPath.set("${project.rootDir}/app/i18n/credentials.json")
    targetSheetUrl.set("https://docs.google.com/spreadsheets/d/12hmQ7U0npYM4hK4ck3qN9bMUDRu-ZcPueluxz5X4w30/edit#gid=1661361434")
    outputXlsxFilePath.set("${project.rootDir}/app/i18n/languages.xlsx")
    rowPositionColumnHeader.set(1)
    doNotConvertNewLine.set(false)
    defaultLanguageForValues.set("en")
    androidResourcePath.set("${project.rootDir}/app/src/main/res")
    outputXmlFileName.set("strings_generated")
}
