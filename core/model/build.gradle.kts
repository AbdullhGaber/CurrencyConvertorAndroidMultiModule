import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.moneyconvertor.jvm)
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlin.serialization.json)
}