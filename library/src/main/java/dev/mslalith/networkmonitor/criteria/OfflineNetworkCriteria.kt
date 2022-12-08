package dev.mslalith.networkmonitor.criteria

import dev.mslalith.networkmonitor.operations.NetworkOperations
import dev.mslalith.networkmonitor.state.NetworkState.Offline
import javax.inject.Inject

internal class OfflineNetworkCriteria @Inject constructor(
    private val networkOperations: NetworkOperations
) : NetworkCriteria<Offline> {

    override fun checkCriteria(): NetworkCriteriaResult<Offline> = with(networkOperations) {
        if (!isWifiConnected() && !isCellularConnected()) criteriaMet(Offline)
        else criteriaNada()
    }
}
