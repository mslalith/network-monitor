package dev.mslalith.networkmonitor.di

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mslalith.networkmonitor.operations.AndroidMNetworkOperations
import dev.mslalith.networkmonitor.operations.LegacyNetworkOperations
import dev.mslalith.networkmonitor.operations.NetworkOperations
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkOperationsModule {

    @Provides
    @Singleton
    internal fun provideNetworkOperations(
        legacyNetworkOperations: LegacyNetworkOperations,
        androidMNetworkOperations: AndroidMNetworkOperations
    ): NetworkOperations = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> androidMNetworkOperations
        else -> legacyNetworkOperations
    }

    @Provides
    @Singleton
    internal fun provideLegacyNetworkOperations(
        connectivityManager: ConnectivityManager
    ): LegacyNetworkOperations = LegacyNetworkOperations(connectivityManager)

    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    @Singleton
    internal fun provideAndroidMNetworkOperations(
        connectivityManager: ConnectivityManager
    ): AndroidMNetworkOperations = AndroidMNetworkOperations(connectivityManager)
}
