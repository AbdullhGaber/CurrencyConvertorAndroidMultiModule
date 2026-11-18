plugins{
    alias(libs.plugins.moneyconvertor.android.library)
    alias(libs.plugins.moneyconvertor.android.room)
    alias(libs.plugins.moneyconvertor.android.hilt)
}

android{
    namespace = "com.iti.database"
}
dependencies{
    implementation(project(":core:model"))
}