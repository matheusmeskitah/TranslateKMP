package core.permissions

public interface IndividualPermissionsHandler {
    public val permissionName: PermissionName

    public fun requestPermission(onResult: OnRequestPermissionResult)

    public fun checkPermission(): PermissionStatus
}