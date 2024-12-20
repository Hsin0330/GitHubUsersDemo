plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo.graphql)
    alias(libs.plugins.kotlin.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.symvox.githubuserlist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.symvox.githubuserlist"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson)
    implementation(libs.gson)
    implementation(libs.coli.compose)
    implementation(libs.coli.network)
    implementation(libs.hilt.android.core)
    kapt(libs.hilt.android.compiler)
    implementation(libs.apollo.graphql.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

apollo {
    service("service") {
        packageName.set("com.symvox.githubuserlist")
        introspection {
            endpointUrl.set("https://docs.github.com/public/fpt/schema.docs.graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}