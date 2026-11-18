import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

plugins {
    alias(libs.plugins.moneyconvertor.android.library)
}

android {
    namespace = "com.iti.testing"
}

dependencies {
    api(kotlin("test"))
    api(project(":core:data"))
    api(project(":core:common"))
    api(project(":core:model"))
    implementation(libs.kotlinx.coroutines.test)
}