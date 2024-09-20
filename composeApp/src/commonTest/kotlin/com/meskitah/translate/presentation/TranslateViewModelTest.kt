package com.meskitah.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.meskitah.translate.data.local.FakeHistoryDAO
import com.meskitah.translate.data.remote.FakeTranslateClient
import core.presentation.UiLanguage
import history.domain.entity.HistoryItemEntity
import history.domain.use_case.GetHistoryUseCase
import history.domain.use_case.SaveLocallyUseCase
import history.presentation.components.UiHistoryItem
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import translate.data.mapper.toUiHistoryItem
import translate.domain.use_case.TranslateUseCase
import translate.presentation.TranslateEvent
import translate.presentation.TranslateState
import translate.presentation.TranslateViewModel

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dao: FakeHistoryDAO

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        client = FakeTranslateClient()
        dao = FakeHistoryDAO()

        val translateUseCase = TranslateUseCase(repository = client)
        val saveLocallyUseCase = SaveLocallyUseCase(dao = dao)
        val getHistoryUseCase = GetHistoryUseCase(dao = dao)

        viewModel = TranslateViewModel(
            translateUseCase = translateUseCase,
            saveLocallyUseCase = saveLocallyUseCase,
            getHistoryUseCase = getHistoryUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `State and history items are properly combined`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItemEntity(
                fromLanguageCode = "en",
                fromText = "a",
                toLanguageCode = "de",
                toText = "b"
            )
            dao.insertHistoryItem(item)

            val uiHistorySate = awaitItem()
            assertThat(uiHistorySate.history.first()).isEqualTo(item.toUiHistoryItem())
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runTest {
        viewModel.state.test {
            assertEquals(expected = TranslateState(), awaitItem())

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            assertEquals(expected = TranslateState(fromText = "test"), awaitItem())

            viewModel.onEvent(TranslateEvent.Translate)
            skipItems(1)

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }
}