package dev.mslalith.networkmonitor

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dev.mslalith.networkmonitor.criteria.InternetNetworkCriteria
import dev.mslalith.networkmonitor.criteria.NetworkCriteriaResult
import dev.mslalith.networkmonitor.criteria.OfflineNetworkCriteria
import dev.mslalith.networkmonitor.criteria.isMet
import dev.mslalith.networkmonitor.state.NetworkState
import dev.mslalith.networkmonitor.state.NetworkState.Undefined
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

internal class NetworkMonitorImpl(
    private val connectivityManager: ConnectivityManager,
    internetNetworkCriteria: InternetNetworkCriteria,
    offlineNetworkCriteria: OfflineNetworkCriteria
) : NetworkMonitor {

    private val criteriaList = listOf(
        internetNetworkCriteria,
        offlineNetworkCriteria
    )

    override fun networkState(): Flow<NetworkState> = callbackFlow {

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                channel.trySend(networkStateSync())
            }

            override fun onLost(network: Network) {
                channel.trySend(networkStateSync())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                channel.trySend(networkStateSync())
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                channel.trySend(networkStateSync())
            }
        }

        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        channel.trySend(networkStateSync())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.conflate()

    override fun networkStateSync(): NetworkState {
        val stateWhichMetCriteria = criteriaList.firstNotNullOfOrNull {
            val result = it.checkCriteria()
            if (result.isMet()) result else null
        }
        return reduceCriteriaResultToState(stateWhichMetCriteria)
    }

    private fun reduceCriteriaResultToState(
        networkCriteriaResult: NetworkCriteriaResult<NetworkState>?
    ): NetworkState = when (networkCriteriaResult) {
        is NetworkCriteriaResult.Met -> networkCriteriaResult.networkState
        NetworkCriteriaResult.Nada, null -> Undefined
    }
}
