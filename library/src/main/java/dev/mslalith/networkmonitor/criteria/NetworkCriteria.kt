package dev.mslalith.networkmonitor.criteria

import dev.mslalith.networkmonitor.state.NetworkState

internal interface NetworkCriteria<T : NetworkState> {
    fun checkCriteria(): NetworkCriteriaResult<T>
}

internal sealed interface NetworkCriteriaResult<out T : NetworkState> {
    data class Met<S : NetworkState>(val networkState: S) : NetworkCriteriaResult<S>
    object Nada : NetworkCriteriaResult<Nothing>
}

internal fun <T : NetworkState> NetworkCriteriaResult<T>.isMet(): Boolean = this is NetworkCriteriaResult.Met

internal fun <T : NetworkState> criteriaMet(networkState: T): NetworkCriteriaResult<T> = NetworkCriteriaResult.Met(networkState)
internal fun criteriaNada(): NetworkCriteriaResult<Nothing> = NetworkCriteriaResult.Nada
