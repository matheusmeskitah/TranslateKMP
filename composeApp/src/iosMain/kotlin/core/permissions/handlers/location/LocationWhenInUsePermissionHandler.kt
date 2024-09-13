package core.permissions.handlers.location

import core.permissions.IndividualPermissionsHandler
import core.permissions.OnRequestPermissionResult
import core.permissions.PermissionName
import core.permissions.PermissionStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined

internal class LocationWhenInUsePermissionHandler : IndividualPermissionsHandler {

    override val permissionName: PermissionName = PermissionName.LOCATION_WHEN_IN_USE

    private val locationManager = CLLocationManager()
    override fun requestPermission(
        onResult: OnRequestPermissionResult
    ) {
        val locationDelegate = LocationDelegate(onResult = onResult)

        locationManager.setDelegate(delegate = locationDelegate)

        locationManager.requestWhenInUseAuthorization()
    }

    override fun checkPermission(): PermissionStatus {
        return when (CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionStatus.GRANTED
            kCLAuthorizationStatusNotDetermined -> PermissionStatus.PENDING
            else -> PermissionStatus.DENIED
        }
    }
}