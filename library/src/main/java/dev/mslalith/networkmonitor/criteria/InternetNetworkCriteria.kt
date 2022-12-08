package dev.mslalith.networkmonitor.criteria

import android.net.wifi.WifiManager
import dev.mslalith.networkmonitor.operations.NetworkOperations
import dev.mslalith.networkmonitor.state.NetworkState.Internet
import dev.mslalith.networkmonitor.utils.activeNetworkSsid
import dev.mslalith.networkmonitor.utils.isUnknownSsid
import javax.inject.Inject

internal class InternetNetworkCriteria @Inject constructor(
    private val wifiManager: WifiManager,
    private val networkOperations: NetworkOperations
) : NetworkCriteria<Internet> {

    override fun checkCriteria(): NetworkCriteriaResult<Internet> = with(networkOperations) {
        when {
            isWifiConnected() -> wifiCriteriaMet()
            isCellularConnected() -> cellularCriteriaMet()
            else -> criteriaNada()
        }
    }

    private fun wifiCriteriaMet(): NetworkCriteriaResult<Internet> {
        val ssid = wifiManager.activeNetworkSsid()
        return when {
            ssid.isUnknownSsid() -> criteriaNada()
            else -> criteriaMet(
                Internet.Wifi(
                    ssid = ssid,
                    hasInternet = networkOperations.isWifiConnected()
                )
            )
        }
    }

    private fun cellularCriteriaMet(): NetworkCriteriaResult<Internet> = criteriaMet(
        Internet.Cellular(hasInternet = networkOperations.isCellularConnected())
    )
}
