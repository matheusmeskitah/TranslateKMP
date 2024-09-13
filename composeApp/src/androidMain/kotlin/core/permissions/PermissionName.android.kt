package core.permissions

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

internal fun PermissionName.toAndroidPermission() = when (this) {
    PermissionName.CAMERA -> CAMERA
    PermissionName.WRITE_EXTERNAL_STORAGE -> WRITE_EXTERNAL_STORAGE
    PermissionName.READ_EXTERNAL_STORAGE -> READ_EXTERNAL_STORAGE
    PermissionName.AUDIO -> RECORD_AUDIO
    PermissionName.LOCATION_ALWAYS -> TODO()
    PermissionName.LOCATION_WHEN_IN_USE -> TODO()
}