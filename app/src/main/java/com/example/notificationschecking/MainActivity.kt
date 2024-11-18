package com.example.notificationschecking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.notificationschecking.ui.theme.NotificationsCheckingTheme
import android.provider.Settings
import android.widget.Toast
import android.content.ComponentName
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationsCheckingTheme {
                NotificationPermissionScreen()
            }
        }
    }

    fun requestNotificationAccess(context: Context) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        context.startActivity(intent)
        Toast.makeText(context, "Предоставьте разрешение на доступ к уведомлениям", Toast.LENGTH_SHORT).show()
    }

    fun isNotificationAccessEnabled(context: Context): Boolean {
        val cn = ComponentName(context, NotificationListener::class.java)
        val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return !enabledListeners.isNullOrEmpty() && enabledListeners.contains(cn.flattenToString())
    }

    @Composable
    fun NotificationPermissionScreen(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var hasPermission by remember { mutableStateOf(isNotificationAccessEnabled(context)) }

        if (hasPermission) {
            Text(
                text = "Доступ к уведомлениям предоставлен",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                style = MaterialTheme.typography.headlineSmall
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Для работы приложения предоставьте доступ к уведомлениям",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = { requestNotificationAccess(context) }) {
                    Text(text = "Предоставить доступ")
                }
            }
        }

        // Перепроверяем статус при возвращении к этому экрану
        LaunchedEffect(Unit) {
            hasPermission = isNotificationAccessEnabled(context)
        }
    }
}

