package dev.mslalith.networkmonitor.operations

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
internal class AndroidMNetworkOperations @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkOperations {

    override fun hasWifi(): Boolean = with(connectivityManager) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }

    override fun hasCellular(): Boolean = with(connectivityManager) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    }

    override fun isWifiConnected(): Boolean = with(connectivityManager) {
        val capabilities = getNetworkCapabilities(activeNetwork)
        hasWifi() && capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun isCellularConnected(): Boolean = with(connectivityManager) {
        val capabilities = getNetworkCapabilities(activeNetwork)
        hasCellular() && capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
