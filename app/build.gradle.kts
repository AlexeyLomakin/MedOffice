import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.alekseilomain.medoffice"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.alekseilomain.medoffice"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Читаем токен DaData из local.properties
        val propsFile = rootProject.file("local.properties")
        val props = Properties().apply {
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            } else {
                throw GradleException("local.properties not found in project root")
            }
        }
        val daDataToken = props.getProperty("dadata.api.token")
            ?: throw GradleException("DaData API token not defined in local.properties")

        // Генерируем поле BuildConfig.DADATA_API_TOKEN
        buildConfigField("String", "DADATA_API_TOKEN", "\"$daDataToken\"")
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }

    buildFeatures {
        compose = true
    }
}

hilt {
    enableAggregatingTask = false
}

dependencies {
    // Project
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)

    // Material
    implementation(libs.material)

    // Тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
}