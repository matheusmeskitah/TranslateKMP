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
import platform.AVFoundation.requestAccessForMediaType

internal class CameraPermissionHandler : IndividualPermissionsHandler {

    override val permissionName: PermissionName = PermissionName.CAMERA
    override fun requestPermission(
        onResult: OnRequestPermissionResult
    ) {
        AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
            when (granted) {
                granted -> onResult(PermissionStatus.GRANTED)
                else -> onResult(PermissionStatus.DENIED)
            }
        }
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