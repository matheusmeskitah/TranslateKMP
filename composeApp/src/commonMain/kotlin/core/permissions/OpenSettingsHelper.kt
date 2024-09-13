package core.permissions

public enum class OpenSettingsResult {
    SUCCESS,
    FAILURE
}

/**
 * A typealias for a callback function that will be called when the method 'open' to open settings screen is completed.
 */
public typealias OnResultCallback = (result: OpenSettingsResult) -> Unit

public interface OpenSettingsHelper {

    /**
     * Opens the settings screen of the app.
     *
     * @param onResult A callback function that will be called when the method is completed.
     */
    public fun open(onResult: OnResultCallback)
}