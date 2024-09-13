package core.permissions

public enum class PermissionStatus {
    /**
     * The permission was granted.
     */
    GRANTED,
    /**
     * The permission is pending, should request.
     */
    PENDING,
    /**
     * The permission was permanently denied. Should be redirected to settings page.
     */
    DENIED
}