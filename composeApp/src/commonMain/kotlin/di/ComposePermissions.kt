package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.permissions.OpenSettingsHelper
import core.permissions.Permissions
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
public fun rememberPermissions(): Permissions = with(localDI()) {
    val permissions by instance<Permissions>()
    return remember { permissions }
}

@Composable
public fun rememberOpenSettings(): OpenSettingsHelper = with(localDI()) {
    val openSettingsHelper by instance<OpenSettingsHelper>()
    return remember { openSettingsHelper }
}