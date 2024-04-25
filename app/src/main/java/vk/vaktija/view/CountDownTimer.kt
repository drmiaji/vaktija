package vk.vaktija.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

@Composable
fun CountDownTimer(timeList: List<String>) {

    val isRunning by remember { mutableStateOf(true) } // Flag to control timer

    var timeLeftData by remember { mutableStateOf(TimeLeftData()) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000) // Update every second
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

fun getNearestTimeInMillis(timeList: List<String>): Pair<Long, Int>? {
    val currentTime = Calendar.getInstance()

    val currentDate = LocalDateTime.now().toLocalDate()

    // Find the first time after the current time
    val index = timeList.indexOfFirst {
        val finalTime = if (it.count() == 4) "0$it" else it

        val timeObj = LocalTime.parse(finalTime)
        val dateTimeObj = LocalDateTime.of(currentDate, timeObj)

        dateTimeObj.atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli() > currentTime.timeInMillis
    }

    if (index == -1) return null

    val timeStr = timeList[index]

    val hours = timeStr.substringBefore(":").toInt()
    val minutes = timeStr.substringAfter(":").toInt()

    val calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()))
    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)
    calendar.set(Calendar.SECOND, 0)

    val nextPrayerTime = calendar.time
    val currentTimeInLocalTime = currentTime.time

    return Pair(nextPrayerTime.time - currentTimeInLocalTime.time, index)
}

private fun formatTime(time: Long): String = if (time < 10) {
    "0${time}"
} else {
    time.toString()
}
