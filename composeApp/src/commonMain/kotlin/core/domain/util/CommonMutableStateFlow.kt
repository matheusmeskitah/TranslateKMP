package core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

expect open class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T> {
    override var value: T
    override val replayCache: List<T>
    override val subscriptionCount: StateFlow<Int>

    override fun compareAndSet(expect: T, update: T): Boolean
    override suspend fun collect(collector: FlowCollector<T>): Nothing
    override suspend fun emit(value: T)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache()
    override fun tryEmit(value: T): Boolean
}

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow() = CommonMutableStateFlow(this)