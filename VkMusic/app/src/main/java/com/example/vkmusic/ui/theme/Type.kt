package com.example.vkmusic.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vkmusic.R

/** Inter — вариативный шрифт (res/font/inter.ttf), нужны API 26+ */
@OptIn(ExperimentalTextApi::class)
private fun inter(weight: Int) = Font(
    resId = R.font.inter,
    weight = FontWeight(weight),
    variationSettings = FontVariation.Settings(FontVariation.weight(weight)),
)

val InterFamily = FontFamily(inter(400), inter(500), inter(600), inter(700))

private fun style(
    size: Int,
    line: Int,
    weight: FontWeight,
    spacing: Float = 0f,
    family: FontFamily = InterFamily,
) = TextStyle(
    fontFamily = family,
    fontSize = size.sp,
    lineHeight = line.sp,
    fontWeight = weight,
    letterSpacing = spacing.sp,
)

/**
 * Соответствие токенов макета и Material-ролей:
 * headline-lg → headlineLarge, headline-md → headlineMedium, title-md → titleMedium,
 * body-md → bodyMedium, body-sm → bodySmall, label-lg/md/sm → labelLarge/Medium/Small
 */
val VkTypography = Typography(
    headlineLarge = style(24, 30, FontWeight.Bold, -0.3f),
    headlineMedium = style(20, 26, FontWeight.Bold, -0.2f),
    titleMedium = style(16, 22, FontWeight.SemiBold, -0.1f),
    bodyMedium = style(15, 20, FontWeight.Normal),
    bodySmall = style(13, 18, FontWeight.Normal),
    labelLarge = style(15, 20, FontWeight.SemiBold),
    labelMedium = style(13, 16, FontWeight.Medium),
    labelSmall = style(11, 14, FontWeight.Medium, 0.1f),
)

object VkText {
    /** mono-sm: 12/16, JetBrains Mono в макете. Здесь системный моноширинный; при желании добавьте шрифт в res/font */
    val MonoSm = style(12, 16, FontWeight.Medium, family = FontFamily.Monospace)
}
