plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.20" apply false
    // С Kotlin 2.0+ компилятор Compose поставляется как Gradle-плагин
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" apply false
}
