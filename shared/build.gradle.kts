plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    //id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            //export("dev.icerock.moko:resources:0.22.3")
            //export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor.common)

            implementation(libs.kotlinx.coroutines)
            api(libs.kotlinx.coroutines)
            api(libs.kotlinx.serialization)

            api(libs.koin.core)
            implementation(libs.koin.test)

            api(libs.napier)

            //api(libs.moko.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            //implementation(libs.moko.resources.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

    task("testClasses")
}

android {
    namespace = "com.alphaomardiallo.freewifiparis"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

/*multiplatformResources {
    multiplatformResourcesPackage = "com.alphaomardiallo.freewifiparis"
    multiplatformResourcesClassName = "SharedRes"
}*/
