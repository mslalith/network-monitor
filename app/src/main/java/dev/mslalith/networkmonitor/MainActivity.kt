package dev.mslalith.networkmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import dev.mslalith.networkmonitor.state.NetworkState
import dev.mslalith.networkmonitor.ui.theme.NetworkMonitorTheme

class MainActivity : ComponentActivity() {

    private val networkMonitor by lazy { NetworkMonitor.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        setContent {
            NetworkMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val networkState by networkMonitor.networkState().collectAsState(initial = NetworkState.Offline)
                    Content(networkState)
                }
            }
        }
    }
}

@Composable
private fun Content(networkState: NetworkState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Network State")
        Text(text = networkState.toString())
    }
}
