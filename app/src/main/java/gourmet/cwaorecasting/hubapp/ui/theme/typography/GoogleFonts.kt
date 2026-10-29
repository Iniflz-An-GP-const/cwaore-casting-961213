package gourmet.cwaorecasting.hubapp.ui.theme.typography

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import gourmet.cwaorecasting.hubapp.R

private val GoogleFontsProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

fun googleFontFamily(name: String): FontFamily {
    val googleFont = GoogleFont(name)

    return FontFamily(
        Font(
            googleFont = googleFont,
            fontProvider = GoogleFontsProvider,
            weight = FontWeight.Normal
        ),
        Font(
            googleFont = googleFont,
            fontProvider = GoogleFontsProvider,
            weight = FontWeight.Medium
        ),
        Font(
            googleFont = googleFont,
            fontProvider = GoogleFontsProvider,
            weight = FontWeight.SemiBold
        ),
        Font(googleFont = googleFont, fontProvider = GoogleFontsProvider, weight = FontWeight.Bold),
    )
}
