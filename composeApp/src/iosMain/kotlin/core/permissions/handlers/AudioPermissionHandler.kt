package core.permissions.handlers

import core.permissions.IndividualPermissionsHandler
import core.permissions.OnRequestPermissionResult
import core.permissions.PermissionName
import core.permissions.PermissionStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType

internal class AudioPermissionHandler : IndividualPermissionsHandler {

    override val permissionName: PermissionName = PermissionName.AUDIO
    override fun requestPermission(
        onResult: OnRequestPermissionResult
    ) {
        //TODO
    }

    override fun checkPermission(): PermissionStatus {
        val authorizationStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        return when (authorizationStatus) {
            AVAuthorizationStatusAuthorized -> PermissionStatus.GRANTED
            AVAuthorizationStatusNotDetermined -> PermissionStatus.PENDING
            else -> PermissionStatus.DENIED
        }
    }
}