package vk.vaktija

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import vk.vaktija.ui.theme.VaktijaTheme
import vk.vaktija.view.HomeScreenView
import vk.vaktija.view.HomeScreenViewModel
import vk.vaktija.view.viewModelFactory
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelId = "alarm_id"
        val channelName = "alarm_name"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)


        setContent {
            VaktijaTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val viewModel = viewModel<HomeScreenViewModel>(factory = viewModelFactory {
                        HomeScreenViewModel(MyApp.appModule.prayerTimeRepository)
                    })
                    HomeScreenView(viewModel)
                }
            }
        }
    }
}