package dev.mslalith.networkmonitor.operations

import android.net.ConnectivityManager
import javax.inject.Inject

@Suppress("DEPRECATION")
internal class LegacyNetworkOperations @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkOperations {

    override fun hasWifi(): Boolean = with(connectivityManager) {
        activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
    }

    override fun hasCellular(): Boolean = with(connectivityManager) {
        activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE
    }

    override fun isWifiConnected(): Boolean = with(connectivityManager) {
        hasWifi() && getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected == true
    }

    override fun isCellularConnected(): Boolean = with(connectivityManager) {
        hasCellular() && getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected == true
    }
}
