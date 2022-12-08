package dev.mslalith.networkmonitor.di

import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mslalith.networkmonitor.NetworkMonitor
import dev.mslalith.networkmonitor.NetworkMonitorImpl
import dev.mslalith.networkmonitor.criteria.InternetNetworkCriteria
import dev.mslalith.networkmonitor.criteria.OfflineNetworkCriteria
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkMonitorModule {

    @Provides
    @Singleton
    internal fun provideItkNetworkMonitor(
        connectivityManager: ConnectivityManager,
        internetNetworkCriteria: InternetNetworkCriteria,
        offlineNetworkCriteria: OfflineNetworkCriteria
    ): NetworkMonitor = NetworkMonitorImpl(
        connectivityManager = connectivityManager,
        internetNetworkCriteria = internetNetworkCriteria,
        offlineNetworkCriteria = offlineNetworkCriteria
    )
}
