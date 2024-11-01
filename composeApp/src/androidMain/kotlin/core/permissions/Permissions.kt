package core.permissions

import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.meskitah.MainActivity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Permissions {
    public actual fun requestPermission(
        permission: PermissionName,
        onResult: OnRequestPermissionResult
    ) {
        val register = MainActivity.instance.activityResultRegistry.register(
            "AndroidPermissionKey",
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            onResult.invoke(if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED)
        }
        register.launch(permission.toAndroidPermission())
    }

    public actual fun checkPermission(permission: PermissionName): PermissionStatus {
        val state = ContextCompat.checkSelfPermission(
            MainActivity.instance,
            permission.toAndroidPermission()
        )
        return when (state) {
            PERMISSION_GRANTED -> PermissionStatus.GRANTED
            else -> {
                if (shouldShowRequestPermissionRationale(
                        MainActivity.instance,
                        permission.toAndroidPermission()
                    )
                )
                    PermissionStatus.DENIED
                else PermissionStatus.PENDING
            }
        }
    }
}