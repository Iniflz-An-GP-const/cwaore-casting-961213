package gourmet.cwaorecasting.hubapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import gourmet.cwaorecasting.hubapp.ui.theme.dimens.AppDimens
import gourmet.cwaorecasting.hubapp.ui.theme.typography.appTypography
import gourmet.cwaorecasting.hubapp.ui.theme.typography.AppFonts
import gourmet.cwaorecasting.hubapp.ui.theme.AppPalette

private val LightColorScheme = lightColorScheme(
    primary = AppPalette.primary,
    onPrimary = AppPalette.onPrimary,

    primaryContainer = AppPalette.primaryContainer,
    onPrimaryContainer = AppPalette.onPrimaryContainer,

    secondary = AppPalette.secondary,
    onSecondary = AppPalette.onSecondary,

    secondaryContainer = AppPalette.secondaryContainer,
    onSecondaryContainer = AppPalette.onSecondaryContainer,

    tertiary = AppPalette.tertiary,
    onTertiary = AppPalette.onTertiary,

    tertiaryContainer = AppPalette.tertiaryContainer,
    onTertiaryContainer = AppPalette.onTertiaryContainer,

    error = AppPalette.error,
    onError = AppPalette.onError,
    errorContainer = AppPalette.errorContainer,
    onErrorContainer = AppPalette.onErrorContainer,

    background = AppPalette.background,
    onBackground = AppPalette.onBackground,

    surface = AppPalette.surface,
    onSurface = AppPalette.onSurface,

    surfaceVariant = AppPalette.surfaceVariant,
    onSurfaceVariant = AppPalette.onSurfaceVariant,

    surfaceTint = AppPalette.surfaceTint,
    surfaceDim = AppPalette.surfaceDim,
    surfaceBright = AppPalette.surfaceBright,

    surfaceContainerLowest = AppPalette.surfaceContainerLowest,
    surfaceContainerLow = AppPalette.surfaceContainerLow,
    surfaceContainer = AppPalette.surfaceContainer,
    surfaceContainerHigh = AppPalette.surfaceContainerHigh,
    surfaceContainerHighest = AppPalette.surfaceContainerHighest,

    outline = AppPalette.outline,
    outlineVariant = AppPalette.outlineVariant,

    inverseSurface = AppPalette.inverseSurface,
    inverseOnSurface = AppPalette.inverseOnSurface,
    inversePrimary = AppPalette.inversePrimary,

    scrim = AppPalette.scrim
)

val LocalDimens = staticCompositionLocalOf { AppDimens }

private val AppTypography = appTypography(AppFonts)

@Composable
fun GTENPAppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDimens provides AppDimens) {

        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}