package com.example.vkmusic.ui.theme

import androidx.compose.ui.graphics.Color

// Токены из tailwind.config макета

val VkPrimary = Color(0xFF0058BF)
val VkOnPrimary = Color(0xFFFFFFFF)
val VkPrimaryContainer = Color(0xFF0077FF)
val VkOnPrimaryContainer = Color(0xFF121212)
val VkSecondary = Color(0xFF405D98)
val VkOnSecondary = Color(0xFFFFFFFF)
val VkSecondaryContainer = Color(0xFFEDF0FF)
val VkOnSecondaryContainer = Color(0xFF002E6A)

val VkSurface = Color(0xFFFFFFFF)
val VkSurfaceContainerLow = Color(0xFFF7F8FA)
val VkSurfaceContainer = Color(0xFFF2F3F5)
val VkSurfaceContainerHigh = Color(0xFFE6E8ED)
val VkOnSurface = Color(0xFF181B23)
val VkOnSurfaceVariant = Color(0xFF555E6B)
val VkOutline = Color(0xFF99A2AD)
val VkOutlineVariant = Color(0xFFE1E3E6)

val VkError = Color(0xFFBA1A1A)
val VkOnError = Color(0xFFFFFFFF)

/** Цвета бренда, которых нет в Material ColorScheme */
object VkBrand {
    val Blue = Color(0xFF0077FF)
    val Cyan = Color(0xFF2B8BFF)
    val Magenta = Color(0xFFF0009C)
    val Purple = Color(0xFF8E24AA)
    val Teal = Color(0xFF009EB5)
    val Success = Color(0xFF1B873F)

    // Градиент hero-блока: from-[#0047AB] via-[#0077FF] to-[#5B21B6]
    val HeroTop = Color(0xFF0047AB)
    val HeroBottom = Color(0xFF5B21B6)
}
