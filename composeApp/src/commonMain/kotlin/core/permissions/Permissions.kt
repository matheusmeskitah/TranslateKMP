package core.permissions

public typealias OnRequestPermissionResult = (permissionStatus: PermissionStatus) -> Unit
public typealias OnRequestPermission = (permissionStatus: Boolean) -> Unit
public typealias CheckSelfPermission = (permission: String) -> Int
public typealias RequestPermission = (permission: PermissionName, onResult: OnRequestPermission) -> Boolean
public typealias ShouldShowRequestPermissionRationale = (permission: String) -> Boolean

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public expect class Permissions {
    public fun requestPermission(permission: PermissionName, onResult: OnRequestPermissionResult)
    public fun checkPermission(permission: PermissionName): PermissionStatus
}