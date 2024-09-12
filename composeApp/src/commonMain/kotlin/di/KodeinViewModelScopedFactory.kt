package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import kotlin.reflect.KClass
import org.kodein.di.DI
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.type.erased

/**
 * Factory class used to create ViewModel instances with Kodein DI container
 * @param di The Kodein DI container instance
 * @param tag An optional tag to filter the bindings
 *
 * @throws DI.NotFoundException if ViewModel class binding is not found in the DI container
 * @see ViewModelProvider.Factory
 */
public class KodeinViewModelScopedSingleton(
    private val di: DI,
    private val tag: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Instance(type = erased(modelClass), tag = tag)
}

@Composable
public inline fun <reified VM : ViewModel> rememberViewModel(
    tag: String? = null
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = { KodeinViewModelScopedSingleton(di = di, tag = tag) }
        )
    }
}