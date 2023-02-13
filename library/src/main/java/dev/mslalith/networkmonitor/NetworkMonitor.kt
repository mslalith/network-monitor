package dev.mslalith.networkmonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import dev.mslalith.networkmonitor.criteria.InternetNetworkCriteria
import dev.mslalith.networkmonitor.criteria.OfflineNetworkCriteria
import dev.mslalith.networkmonitor.operations.AndroidMNetworkOperations
import dev.mslalith.networkmonitor.operations.LegacyNetworkOperations
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
    companion object
}

fun NetworkMonitor.Companion.getInstance(context: Context): NetworkMonitor {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val networkOperations = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        AndroidMNetworkOperations(connectivityManager = connectivityManager)
    } else {
        LegacyNetworkOperations(connectivityManager = connectivityManager)
    }
    return NetworkMonitorImpl(
        connectivityManager = connectivityManager,
        internetNetworkCriteria = InternetNetworkCriteria(
            wifiManager = wifiManager,
            networkOperations = networkOperations
        ),
        offlineNetworkCriteria = OfflineNetworkCriteria(networkOperations = networkOperations)
    )
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
