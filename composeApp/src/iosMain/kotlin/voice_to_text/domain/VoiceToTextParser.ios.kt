package voice_to_text.domain

import kotlin.OptIn
import kotlin.String
import kotlin.Throwable
import kotlin.Unit
import kotlin.let
import kotlin.run
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioInputNode
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryOptionDuckOthers
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVAudioSessionModeSpokenAudio
import platform.AVFAudio.AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation
import platform.AVFAudio.setActive
import platform.Foundation.NSLocale
import platform.Speech.SFSpeechAudioBufferRecognitionRequest
import platform.Speech.SFSpeechRecognitionTask
import platform.Speech.SFSpeechRecognizer
import platform.Speech.SFSpeechRecognizerAuthorizationStatus
import voice_to_text.domain.model.VoiceToTextParserState

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class VoiceToTextParser {

    private val _state = MutableStateFlow(
        VoiceToTextParserState(result = "", error = null, powerRatio = 0f, isSpeaking = false)
    )
    actual val state: StateFlow<VoiceToTextParserState> = _state.asStateFlow()

    private var recognizer: SFSpeechRecognizer? = null
    private var audioEngine: AVAudioEngine? = null
    private var inputNode: AVAudioInputNode? = null
    private var audioBufferRequest: SFSpeechAudioBufferRecognitionRequest? = null
    private var recognitionTask: SFSpeechRecognitionTask? = null
    private var audioSession: AVAudioSession? = null

    actual fun cancel() {
        // Not needed on iOS
    }

    actual fun reset() {
        stopListening()
        _state.update {
            VoiceToTextParserState(result = "", error = null, powerRatio = 0f, isSpeaking = false)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun startListening(languageCode: String, errorMsg: String) {
        _state.update { it.copy(error = null) }

        val chosenLocale = NSLocale(languageCode)
        val supportedLocale = if (SFSpeechRecognizer.supportedLocales().contains(chosenLocale)) {
            chosenLocale
        } else {
            NSLocale("en-US")
        }

        recognizer = SFSpeechRecognizer(locale = supportedLocale)

        if (recognizer?.isAvailable() != true) {
            _state.update { it.copy(error = "Speech recognizer is not available") }
            return
        }

        audioSession = AVAudioSession.sharedInstance()

        requestPermissions {
            audioBufferRequest = SFSpeechAudioBufferRecognitionRequest()

            audioBufferRequest?.let {
                recognitionTask = recognizer?.recognitionTaskWithRequest(it) { result, error ->
                    result?.let {
                        if (result.isFinal()) {
                            _state.update { currentState ->
                                currentState.copy(
                                    result = result.bestTranscription().formattedString()
                                )
                            }
                        }
                    } ?: run {
                        _state.update { currentState ->
                            currentState.copy(error = error?.localizedDescription)
                        }
                    }
                }
            }

            audioEngine = AVAudioEngine()
            inputNode = audioEngine?.inputNode

            val recordingFormat = inputNode?.outputFormatForBus(0u)
            inputNode?.installTapOnBus(
                0u,
                bufferSize = 1024u,
                format = recordingFormat
            ) { buffer, _ ->
                buffer?.let { audioBufferRequest?.appendAudioPCMBuffer(it) }
            }

            audioEngine?.prepare()

            try {
                audioSession?.setCategory(
                    category = AVAudioSessionCategoryPlayAndRecord,
                    mode = AVAudioSessionModeSpokenAudio,
                    options = AVAudioSessionCategoryOptionDuckOthers,
                    error = null
                )
                audioSession?.setActive(
                    active = true,
                    withOptions = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation,
                    error = null
                )

                audioEngine?.startAndReturnError(null)

                _state.update { currentState ->
                    currentState.copy(isSpeaking = true)
                }
            } catch (error: Throwable) {
                _state.update { currentState ->
                    currentState.copy(error = error.message, isSpeaking = false)
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun stopListening() {
        _state.update { currentState ->
            currentState.copy(isSpeaking = false)
        }

        audioBufferRequest?.endAudio()
        audioBufferRequest = null

        audioEngine?.stop()

        inputNode?.removeTapOnBus(0u)

        try {
            audioSession?.setActive(false, null)
        } catch (e: Throwable) {
            // Handle error
        }
        audioSession = null
    }

    private fun requestPermissions(onGranted: () -> Unit) {
        audioSession?.requestRecordPermission { wasGranted ->
            if (!wasGranted) {
                _state.update {
                    it.copy(error = "You need to grant permission to record your voice.")
                }
                stopListening()
                return@requestRecordPermission
            }

            SFSpeechRecognizer.requestAuthorization { status ->
                if (status != SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized) {
                    _state.update {
                        it.copy(error = "You need to grant permission to transcribe audio.")
                    }
                    stopListening()
                    return@requestAuthorization
                }
                onGranted()
            }
        }
    }
}