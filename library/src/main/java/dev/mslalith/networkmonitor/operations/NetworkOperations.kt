package dev.mslalith.networkmonitor.operations

internal interface NetworkOperations {
    fun hasWifi(): Boolean
    fun hasCellular(): Boolean
    fun isWifiConnected(): Boolean
    fun isCellularConnected(): Boolean
}
