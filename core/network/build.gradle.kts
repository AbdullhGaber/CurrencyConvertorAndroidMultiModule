plugins {
    alias(libs.plugins.moneyconvertor.android.library)
    alias(libs.plugins.moneyconvertor.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.iti.network"
}

dependencies{
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.logger)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.kotlin.serialization.json)
    implementation(project(":core:model"))
}
