package translate.di

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import translate.data.KtorTranslateClient
import translate.data.local.HistoryDAO
import translate.domain.history.HistoryDataSource
import translate.domain.use_case.TranslateUseCase
import translate.presentation.TranslateViewModel

public val mainModule: DI.Module = DI.Module("main-module") {

    bindSingleton<HttpClient> {
        HttpClient()
    }

    bindSingleton<HistoryDataSource> {
        HistoryDAO()
    }

    bindProvider {
        TranslateViewModel(
            translateUseCase = TranslateUseCase(
                client = KtorTranslateClient(httpClient = instance()),
                historyDataSource = instance()
            ),
            historyDataSource = instance()
        )
    }
}