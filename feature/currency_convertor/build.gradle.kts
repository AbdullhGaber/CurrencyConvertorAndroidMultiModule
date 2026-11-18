plugins{
    alias(libs.plugins.moneyconvertor.android.library)
    alias(libs.plugins.moneyconvertor.android.feature)
    alias(libs.plugins.moneyconvertor.android.compose)
}

android {
    namespace = "com.iti.currency_convertor"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies{
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))

    testImplementation(libs.mockito)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
