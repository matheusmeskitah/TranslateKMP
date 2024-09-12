package di

import history.data.local.HistoryDAO
import history.domain.HistoryDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import translate.data.TranslateRepositoryImpl
import translate.domain.use_case.TranslateUseCase
import translate.presentation.TranslateViewModel
import voice_to_text.presentation.VoiceToTextViewModel

expect fun DI.Builder.platformBind()

val mainModule: DI.Module = DI.Module("main-module") {

    bindSingleton<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    bindSingleton<HistoryDataSource> {
        HistoryDAO()
    }

    bindProvider {
        TranslateViewModel(
            translateUseCase = TranslateUseCase(
                client = TranslateRepositoryImpl(httpClient = instance()),
                historyDataSource = instance()
            ),
            historyDataSource = instance()
        )
    }

    bindProvider {
        VoiceToTextViewModel(parser = instance())
    }

    platformBind()
}