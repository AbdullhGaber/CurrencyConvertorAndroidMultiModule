plugins {
    `kotlin-dsl`
}

group = "com.iti.mongez.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}
gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "moneyConvertor.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("jvmLibrary") {
            id = "moneyConvertor.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "moneyConvertor.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "moneyConvertor.android.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeature") {
            id = "moneyConvertor.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidRoom") {
            id = "moneyConvertor.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}