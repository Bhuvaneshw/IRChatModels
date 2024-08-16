package com.acutecoder.irchat.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

internal val primaryLight = Color(0xFF505B92)
internal val onPrimaryLight = Color(0xFFFFFFFF)
internal val primaryContainerLight = Color(0xFFDDE1FF)
internal val onPrimaryContainerLight = Color(0xFF08164B)
internal val secondaryLight = Color(0xFF4F5B92)
internal val onSecondaryLight = Color(0xFFFFFFFF)
internal val secondaryContainerLight = Color(0xFFDDE1FF)
internal val onSecondaryContainerLight = Color(0xFF07164B)
internal val tertiaryLight = Color(0xFF4F5B92)
internal val onTertiaryLight = Color(0xFFFFFFFF)
internal val tertiaryContainerLight = Color(0xFFDDE1FF)
internal val onTertiaryContainerLight = Color(0xFF06164B)
internal val errorLight = Color(0xFFBA1A1A)
internal val onErrorLight = Color(0xFFFFFFFF)
internal val errorContainerLight = Color(0xFFFFDAD6)
internal val onErrorContainerLight = Color(0xFF410002)
internal val backgroundLight = Color(0xFFFBF8FF)
internal val onBackgroundLight = Color(0xFF1B1B21)
internal val surfaceLight = Color(0xFFFBF8FF)
internal val onSurfaceLight = Color(0xFF1B1B21)
internal val surfaceVariantLight = Color(0xFFE3E1EC)
internal val onSurfaceVariantLight = Color(0xFF45464F)
internal val outlineLight = Color(0xFF767680)
internal val outlineVariantLight = Color(0xFFC6C5D0)
internal val scrimLight = Color(0xFF000000)
internal val inverseSurfaceLight = Color(0xFF303036)
internal val inverseOnSurfaceLight = Color(0xFFF2F0F7)
internal val inversePrimaryLight = Color(0xFFB9C3FF)
internal val surfaceDimLight = Color(0xFFDBD9E0)
internal val surfaceBrightLight = Color(0xFFFBF8FF)
internal val surfaceContainerLowestLight = Color(0xFFFFFFFF)
internal val surfaceContainerLowLight = Color(0xFFF5F2FA)
internal val surfaceContainerLight = Color(0xFFEFEDF4)
internal val surfaceContainerHighLight = Color(0xFFE9E7EF)
internal val surfaceContainerHighestLight = Color(0xFFE3E1E9)

internal val primaryDark = Color(0xFFB9C3FF)
internal val onPrimaryDark = Color(0xFF212C61)
internal val primaryContainerDark = Color(0xFF384379)
internal val onPrimaryContainerDark = Color(0xFFDDE1FF)
internal val secondaryDark = Color(0xFFB8C3FF)
internal val onSecondaryDark = Color(0xFF202C61)
internal val secondaryContainerDark = Color(0xFF374379)
internal val onSecondaryContainerDark = Color(0xFFDDE1FF)
internal val tertiaryDark = Color(0xFFB8C4FF)
internal val onTertiaryDark = Color(0xFF1F2C61)
internal val tertiaryContainerDark = Color(0xFF374379)
internal val onTertiaryContainerDark = Color(0xFFDDE1FF)
internal val errorDark = Color(0xFFFFB4AB)
internal val onErrorDark = Color(0xFF690005)
internal val errorContainerDark = Color(0xFF93000A)
internal val onErrorContainerDark = Color(0xFFFFDAD6)
internal val backgroundDark = Color(0xFF121318)
internal val onBackgroundDark = Color(0xFFE3E1E9)
internal val surfaceDark = Color(0xFF121318)
internal val onSurfaceDark = Color(0xFFE3E1E9)
internal val surfaceVariantDark = Color(0xFF45464F)
internal val onSurfaceVariantDark = Color(0xFFC6C5D0)
internal val outlineDark = Color(0xFF90909A)
internal val outlineVariantDark = Color(0xFF45464F)
internal val scrimDark = Color(0xFF000000)
internal val inverseSurfaceDark = Color(0xFFE3E1E9)
internal val inverseOnSurfaceDark = Color(0xFF303036)
internal val inversePrimaryDark = Color(0xFF505B92)
internal val surfaceDimDark = Color(0xFF121318)
internal val surfaceBrightDark = Color(0xFF38393F)
internal val surfaceContainerLowestDark = Color(0xFF0D0E13)
internal val surfaceContainerLowDark = Color(0xFF1B1B21)
internal val surfaceContainerDark = Color(0xFF1F1F25)
internal val surfaceContainerHighDark = Color(0xFF292A2F)
internal val surfaceContainerHighestDark = Color(0xFF34343A)

class ThemeColorScheme(var isDark: Boolean, scheme: ColorScheme) {
    val light = scheme.onSecondary
    val dark = scheme.onSecondaryContainer
    val primary = scheme.primary
    val primaryContainer = if (isDark) scheme.primaryContainer else scheme.primary
    val secondary =
        if (isDark) scheme.primaryContainer.copy(alpha = 0.5f) else scheme.primaryContainer
    val background = if (isDark) Color(0xee111111) else Color(0xffF1F1F1)
    val secondaryContainer = if (isDark) Color.Black else Color.White
    val white = Color.White
    val black = Color.Black
}
