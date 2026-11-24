package com.example.vkmusic.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val VkColorScheme = lightColorScheme(
    primary = VkPrimary,
    onPrimary = VkOnPrimary,
    primaryContainer = VkPrimaryContainer,
    onPrimaryContainer = VkOnPrimaryContainer,
    secondary = VkSecondary,
    onSecondary = VkOnSecondary,
    secondaryContainer = VkSecondaryContainer,
    onSecondaryContainer = VkOnSecondaryContainer,
    background = VkSurface,
    onBackground = VkOnSurface,
    surface = VkSurface,
    onSurface = VkOnSurface,
    surfaceVariant = VkSurfaceContainer,
    onSurfaceVariant = VkOnSurfaceVariant,
    surfaceContainerLow = VkSurfaceContainerLow,
    surfaceContainer = VkSurfaceContainer,
    surfaceContainerHigh = VkSurfaceContainerHigh,
    outline = VkOutline,
    outlineVariant = VkOutlineVariant,
    error = VkError,
    onError = VkOnError,
)

// borderRadius из tailwind: sm 8, md 12, lg 16, xl 20
private val VkShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(20.dp),
)

@Composable
fun VkMusicTheme(content: @Composable () -> Unit) {
    // Макет только светлый (darkMode: 'class', но тёмная тема не нарисована)
    MaterialTheme(
        colorScheme = VkColorScheme,
        typography = VkTypography,
        shapes = VkShapes,
        content = content,
    )
}
