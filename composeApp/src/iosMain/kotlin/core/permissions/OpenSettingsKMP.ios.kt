package core.permissions

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class OpenSettingsKMP : OpenSettingsHelper {
    actual override fun open(onResult: OnResultCallback) {
        runCatching {
            val url = NSURL.URLWithString(UIApplicationOpenSettingsURLString)
            val result = url?.let { UIApplication.sharedApplication.openURL(it) }

            when (result) {
                true -> onResult(OpenSettingsResult.SUCCESS)
                else -> onResult(OpenSettingsResult.FAILURE)
            }
        }.onFailure {
            onResult(OpenSettingsResult.FAILURE)
        }
    }
}