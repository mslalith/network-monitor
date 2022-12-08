package dev.mslalith.networkmonitor

import dev.mslalith.networkmonitor.state.NetworkState
import dev.mslalith.networkmonitor.state.NetworkState.Internet
import dev.mslalith.networkmonitor.state.NetworkState.Offline
import dev.mslalith.networkmonitor.state.NetworkState.Undefined
import dev.mslalith.networkmonitor.utils.isUnknownSsid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface NetworkMonitor {
    fun networkState(): Flow<NetworkState>
    fun networkStateSync(): NetworkState
}

fun Flow<NetworkState>.skipUndefined(): Flow<NetworkState> = filterNot { it is Undefined }

fun NetworkMonitor.ssidChanges(): Flow<String> = networkState()
    .map { networkState ->
        when (networkState) {
            is Internet.Wifi -> networkState.ssid
            is Internet.Cellular, Offline, Undefined -> null
        }
    }
    .filterNot { it.isUnknownSsid() }
    .filterNotNull()
