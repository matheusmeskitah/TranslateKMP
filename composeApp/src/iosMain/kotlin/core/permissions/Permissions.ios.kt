package core.permissions

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
public actual class Permissions {
    public actual fun requestPermission(
        permission: PermissionName,
        onResult: OnRequestPermissionResult
    ) {
        permission.permissionHandler.requestPermission {
            onResult(it)
        }
    }

    public actual fun checkPermission(permission: PermissionName): PermissionStatus {
        return permission.permissionHandler.checkPermission()
    }
}