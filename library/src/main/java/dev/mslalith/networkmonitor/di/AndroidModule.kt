package dev.mslalith.networkmonitor.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AndroidModule {

    @Provides
    @Singleton
    internal fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    internal fun provideWifiManager(@ApplicationContext context: Context): WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
}
