package dev.mslalith.networkmonitor.di

import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mslalith.networkmonitor.criteria.InternetNetworkCriteria
import dev.mslalith.networkmonitor.criteria.OfflineNetworkCriteria
import dev.mslalith.networkmonitor.operations.NetworkOperations
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkCriteriaModule {

    @Provides
    @Singleton
    internal fun provideInternetNetworkCriteria(
        wifiManager: WifiManager,
        networkOperations: NetworkOperations
    ): InternetNetworkCriteria = InternetNetworkCriteria(wifiManager, networkOperations)

    @Provides
    @Singleton
    internal fun provideOfflineNetworkCriteria(
        networkOperations: NetworkOperations
    ): OfflineNetworkCriteria = OfflineNetworkCriteria(networkOperations)
}
