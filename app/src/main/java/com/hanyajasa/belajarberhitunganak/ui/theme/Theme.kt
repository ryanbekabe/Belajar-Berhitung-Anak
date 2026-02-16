package com.hanyajasa.belajarberhitunganak.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = BiruCerah,
    onPrimary = Putih,
    primaryContainer = BiruCerahTua,
    onPrimaryContainer = Putih,
    secondary = HijauCerah,
    onSecondary = Putih,
    secondaryContainer = HijauCerah,
    onSecondaryContainer = Putih,
    tertiary = KuningCerah,
    onTertiary = Hitam,
    tertiaryContainer = KuningCerah,
    onTertiaryContainer = Hitam,
    background = LatarBelakangCerah,
    onBackground = Hitam,
    surface = Putih,
    onSurface = Hitam,
    surfaceVariant = LatarBelakangCerah,
    onSurfaceVariant = Hitam,
    error = MerahCerah,
    onError = Putih
)

private val DarkColorScheme = darkColorScheme(
    primary = BiruCerah,
    onPrimary = Hitam,
    primaryContainer = BiruTua,
    onPrimaryContainer = Putih,
    secondary = HijauCerah,
    onSecondary = Hitam,
    secondaryContainer = HijauTua,
    onSecondaryContainer = Putih,
    tertiary = KuningCerah,
    onTertiary = Hitam,
    tertiaryContainer = KuningCerah,
    onTertiaryContainer = Hitam,
    background = Hitam,
    onBackground = Putih,
    surface = Hitam,
    onSurface = Putih,
    surfaceVariant = BiruTua,
    onSurfaceVariant = Putih,
    error = MerahCerah,
    onError = Putih
)

@Composable
fun BelajarBerhitungAnakTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
