package core.permissions

import core.permissions.handlers.AudioPermissionHandler
import core.permissions.handlers.CameraPermissionHandler
import core.permissions.handlers.location.LocationAlwaysPermissionHandler
import core.permissions.handlers.location.LocationWhenInUsePermissionHandler

internal val PermissionName.permissionHandler: IndividualPermissionsHandler
    get() = when (this) {
        PermissionName.CAMERA -> CameraPermissionHandler()
        PermissionName.LOCATION_WHEN_IN_USE -> LocationWhenInUsePermissionHandler()
        PermissionName.LOCATION_ALWAYS -> LocationAlwaysPermissionHandler()
        PermissionName.AUDIO -> AudioPermissionHandler()
        PermissionName.WRITE_EXTERNAL_STORAGE -> TODO()
        PermissionName.READ_EXTERNAL_STORAGE -> TODO()
    }