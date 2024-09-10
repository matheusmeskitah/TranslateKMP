package core.presentation

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val AccentViolet = Color(0xFF5643C9)
val LightBlue = Color(0xFFA8A5BB)
val LightBlueGrey = Color(0xFFF6F4F4)
val TextBlack = Color(0xFF111111)
val DarkGrey = Color(0xFF282C31)

val lightColors = lightColorScheme(
    primary = AccentViolet,
    background = LightBlueGrey,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack
)

val darkColors = darkColorScheme(
    primary = AccentViolet,
    background = DarkGrey,
    onPrimary = Color.White,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White
)