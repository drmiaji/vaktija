package vk.vaktija.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import vk.vaktija.utils.formatTime
import vk.vaktija.utils.getNearestTimeInMillis

@Composable
fun CountDownTimerView(timeList: List<String>) {
    val isRunning by remember { mutableStateOf(true) }
    var timeLeftData by remember { mutableStateOf(TimeLeftData()) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            val value = getNearestTimeInMillis(timeList) ?: return@LaunchedEffect
            val seconds = value.first / 1000
            val minutes = seconds / 60
            val hours = minutes / 60

            val minutesFormatted = minutes % 60
            val secondsFormatted = seconds % 60

            val secondsAsString = formatTime(secondsFormatted)
            val minutesAsString = formatTime(minutesFormatted)
            val hoursAsString = formatTime(hours)

            timeLeftData = TimeLeftData(hoursAsString, minutesAsString, secondsAsString)
            delay(1000)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = timeLeftData.hours, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = ":")
            Text(text = timeLeftData.minutes, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = ":")
            Text(text = timeLeftData.seconds, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}
