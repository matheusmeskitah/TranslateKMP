package core.presentation

import core.domain.language.Language
import org.jetbrains.compose.resources.DrawableResource
import translatekmp.composeapp.generated.resources.Res
import translatekmp.composeapp.generated.resources.arabic
import translatekmp.composeapp.generated.resources.azerbaijani
import translatekmp.composeapp.generated.resources.chinese
import translatekmp.composeapp.generated.resources.czech
import translatekmp.composeapp.generated.resources.danish
import translatekmp.composeapp.generated.resources.dutch
import translatekmp.composeapp.generated.resources.english
import translatekmp.composeapp.generated.resources.finnish
import translatekmp.composeapp.generated.resources.french
import translatekmp.composeapp.generated.resources.german
import translatekmp.composeapp.generated.resources.greek
import translatekmp.composeapp.generated.resources.hebrew
import translatekmp.composeapp.generated.resources.hindi
import translatekmp.composeapp.generated.resources.hungarian
import translatekmp.composeapp.generated.resources.indonesian
import translatekmp.composeapp.generated.resources.irish
import translatekmp.composeapp.generated.resources.italian
import translatekmp.composeapp.generated.resources.japanese
import translatekmp.composeapp.generated.resources.korean
import translatekmp.composeapp.generated.resources.persian
import translatekmp.composeapp.generated.resources.polish
import translatekmp.composeapp.generated.resources.portuguese
import translatekmp.composeapp.generated.resources.russian
import translatekmp.composeapp.generated.resources.slovak
import translatekmp.composeapp.generated.resources.spanish
import translatekmp.composeapp.generated.resources.swedish
import translatekmp.composeapp.generated.resources.turkish
import translatekmp.composeapp.generated.resources.ukrainian

class UiLanguage(
    val drawableRes: DrawableResource,
    val language: Language
) {
    companion object {
        fun byCode(langCode: String): UiLanguage {
            return allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }

        val allLanguages: List<UiLanguage>
            get() = Language.entries.map { language ->
                UiLanguage(
                    language = language,
                    drawableRes = when (language) {
                        Language.ENGLISH -> Res.drawable.english
                        Language.ARABIC -> Res.drawable.arabic
                        Language.AZERBAIJANI -> Res.drawable.azerbaijani
                        Language.CHINESE -> Res.drawable.chinese
                        Language.CZECH -> Res.drawable.czech
                        Language.DANISH -> Res.drawable.danish
                        Language.DUTCH -> Res.drawable.dutch
                        Language.FINNISH -> Res.drawable.finnish
                        Language.FRENCH -> Res.drawable.french
                        Language.GERMAN -> Res.drawable.german
                        Language.GREEK -> Res.drawable.greek
                        Language.HEBREW -> Res.drawable.hebrew
                        Language.HINDI -> Res.drawable.hindi
                        Language.HUNGARIAN -> Res.drawable.hungarian
                        Language.INDONESIAN -> Res.drawable.indonesian
                        Language.IRISH -> Res.drawable.irish
                        Language.ITALIAN -> Res.drawable.italian
                        Language.JAPANESE -> Res.drawable.japanese
                        Language.KOREAN -> Res.drawable.korean
                        Language.PERSIAN -> Res.drawable.persian
                        Language.POLISH -> Res.drawable.polish
                        Language.PORTUGUESE -> Res.drawable.portuguese
                        Language.RUSSIAN -> Res.drawable.russian
                        Language.SLOVAK -> Res.drawable.slovak
                        Language.SPANISH -> Res.drawable.spanish
                        Language.SWEDISH -> Res.drawable.swedish
                        Language.TURKISH -> Res.drawable.turkish
                        Language.UKRAINIAN -> Res.drawable.ukrainian
                    }
                )
            }.sortedBy { it.language.langName }
    }
}