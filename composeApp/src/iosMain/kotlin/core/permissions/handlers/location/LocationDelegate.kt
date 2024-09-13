package core.permissions.handlers.location

import core.permissions.OnRequestPermissionResult
import core.permissions.PermissionStatus
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject

public class LocationDelegate(private val onResult: OnRequestPermissionResult) :
    NSObject(),
    CLLocationManagerDelegateProtocol {
    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        when (didChangeAuthorizationStatus) {
            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> onResult(
                PermissionStatus.GRANTED
            )

            kCLAuthorizationStatusNotDetermined -> {
                // ignoring not Determined status here,
                // because delegate will be called when CLLocationManager were instantiated and there is no use for this status at this time
                // ref -> https://developer.apple.com/documentation/corelocation/cllocationmanagerdelegate/locationmanagerdidchangeauthorization(_:)
            }

            else -> onResult(PermissionStatus.DENIED)
        }
    }
}