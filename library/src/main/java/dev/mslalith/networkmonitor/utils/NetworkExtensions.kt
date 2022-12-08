package dev.mslalith.networkmonitor.utils

import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager

@Suppress("DEPRECATION")
internal fun WifiManager.activeNetworkSsid(): String? = connectionInfo
    ?.takeIf { it.supplicantState == SupplicantState.COMPLETED }
    ?.ssid
    ?.replace("\"".toRegex(), "")

internal fun String?.isUnknownSsid(): Boolean = isNullOrEmpty() || equals("<unknown ssid>")
