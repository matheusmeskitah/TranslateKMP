package core.permissions

public typealias OpenSettings = () -> Boolean

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class OpenSettingsKMP(
    private val settingsOpener: OpenSettings
) : OpenSettingsHelper {

    public actual override fun open(onResult: OnResultCallback) {
        runCatching {
            val result = settingsOpener()
            when (result) {
                true -> onResult(OpenSettingsResult.SUCCESS)
                else -> onResult(OpenSettingsResult.FAILURE)
            }
        }.onFailure {
            onResult(OpenSettingsResult.FAILURE)
        }
    }
}