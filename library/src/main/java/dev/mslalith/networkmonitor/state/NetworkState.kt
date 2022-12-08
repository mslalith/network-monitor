package dev.mslalith.networkmonitor.state

sealed interface NetworkState {

    sealed class Internet(
        open val hasInternet: Boolean
    ) : NetworkState {

        data class Wifi(
            val ssid: String?,
            override val hasInternet: Boolean
        ) : Internet(hasInternet)

        data class Cellular(
            override val hasInternet: Boolean
        ) : Internet(hasInternet)
    }

    object Offline : NetworkState
    object Undefined : NetworkState
}
