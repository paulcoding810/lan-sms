package com.paulcoding.lansms

import android.Manifest.permission.RECEIVE_SMS
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paulcoding.lansms.ui.theme.LANSMSTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestSMSPermissions()
        val prefsManager = PrefsManager(this)
        setContent {
            LANSMSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(
                        modifier = Modifier.padding(innerPadding),
                        prefsManager = prefsManager
                    )
                }
            }
        }
    }

    private fun requestSMSPermissions() {
        requestPermissions(listOf(RECEIVE_SMS).toTypedArray(), 10)
    }
}


@Composable
fun Home(modifier: Modifier = Modifier, prefsManager: PrefsManager) {
    val scope = rememberCoroutineScope()
    var ip by remember { mutableStateOf(prefsManager.getIP().let { it.ifEmpty { "192.168.0.4" } }) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(modifier = Modifier.fillMaxWidth(), value = ip, onValueChange = {
            ip = it
            prefsManager.setIP(it)
        })
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                sendMessageToLAN("Hello from Android", "192.168.0.4")
            }
        }) {
            Text("Send Message")
        }
    }
}
