package gourmet.cwaorecasting.hubapp.ui.theme.typography

import androidx.compose.material3.Typography
import gourmet.cwaorecasting.hubapp.ui.theme.typography.Fonts

fun appTypography(fonts: Fonts): Typography {
    val default = Typography()

    return Typography(
        displayLarge = default.displayLarge.copy(fontFamily = fonts.display),
        displayMedium = default.displayMedium.copy(fontFamily = fonts.display),
        displaySmall = default.displaySmall.copy(fontFamily = fonts.display),

        headlineLarge = default.headlineLarge.copy(fontFamily = fonts.display),
        headlineMedium = default.headlineMedium.copy(fontFamily = fonts.display),
        headlineSmall = default.headlineSmall.copy(fontFamily = fonts.display),

        titleLarge = default.titleLarge.copy(fontFamily = fonts.display),
        titleMedium = default.titleMedium.copy(fontFamily = fonts.display),
        titleSmall = default.titleSmall.copy(fontFamily = fonts.display),

        bodyLarge = default.bodyLarge.copy(fontFamily = fonts.body),
        bodyMedium = default.bodyMedium.copy(fontFamily = fonts.body),
        bodySmall = default.bodySmall.copy(fontFamily = fonts.body),

        labelLarge = default.labelLarge.copy(fontFamily = fonts.body),
        labelMedium = default.labelMedium.copy(fontFamily = fonts.body),
        labelSmall = default.labelSmall.copy(fontFamily = fonts.body),
    )
}
