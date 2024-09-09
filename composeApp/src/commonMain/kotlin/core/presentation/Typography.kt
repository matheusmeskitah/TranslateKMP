package core.presentation

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import translatekmp.composeapp.generated.resources.Montserrat_Black
import translatekmp.composeapp.generated.resources.Montserrat_BlackItalic
import translatekmp.composeapp.generated.resources.Montserrat_Bold
import translatekmp.composeapp.generated.resources.Montserrat_BoldItalic
import translatekmp.composeapp.generated.resources.Montserrat_ExtraBold
import translatekmp.composeapp.generated.resources.Montserrat_ExtraBoldItalic
import translatekmp.composeapp.generated.resources.Montserrat_ExtraLight
import translatekmp.composeapp.generated.resources.Montserrat_ExtraLightItalic
import translatekmp.composeapp.generated.resources.Montserrat_Italic
import translatekmp.composeapp.generated.resources.Montserrat_Light
import translatekmp.composeapp.generated.resources.Montserrat_LightItalic
import translatekmp.composeapp.generated.resources.Montserrat_Medium
import translatekmp.composeapp.generated.resources.Montserrat_MediumItalic
import translatekmp.composeapp.generated.resources.Montserrat_Regular
import translatekmp.composeapp.generated.resources.Montserrat_SemiBold
import translatekmp.composeapp.generated.resources.Montserrat_SemiBoldItalic
import translatekmp.composeapp.generated.resources.Montserrat_Thin
import translatekmp.composeapp.generated.resources.Montserrat_ThinItalic
import translatekmp.composeapp.generated.resources.Res

@Composable
fun getFontFamily() = FontFamily(
    Font(Res.font.Montserrat_Black, FontWeight.Black),
    Font(Res.font.Montserrat_BlackItalic, FontWeight.Black, FontStyle.Italic),
    Font(Res.font.Montserrat_Bold, FontWeight.Bold),
    Font(Res.font.Montserrat_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.Montserrat_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.Montserrat_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.Montserrat_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.Montserrat_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.Montserrat_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.Montserrat_Light, FontWeight.Light),
    Font(Res.font.Montserrat_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.Montserrat_Medium, FontWeight.Medium),
    Font(Res.font.Montserrat_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.Montserrat_Regular, FontWeight.Normal),
    Font(Res.font.Montserrat_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Montserrat_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.Montserrat_Thin, FontWeight.Thin),
    Font(Res.font.Montserrat_ThinItalic, FontWeight.Thin, FontStyle.Italic)
)

@Composable
fun getTypography() = Typography(
    displayLarge = TextStyle(fontFamily = getFontFamily()),
    displayMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 18.sp
    ),
    displaySmall = TextStyle(fontFamily = getFontFamily()),
    headlineLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 22.sp
    ),
    headlineSmall = TextStyle(fontFamily = getFontFamily()),
    titleLarge = TextStyle(fontFamily = getFontFamily()),
    titleMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(fontFamily = getFontFamily()),
    bodyLarge = TextStyle(fontFamily = getFontFamily()),
    bodyMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(fontFamily = getFontFamily()),
    labelLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(fontFamily = getFontFamily()),
)