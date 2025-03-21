package vk.vaktija.ui

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import vk.vaktija.utils.Constants.ONE_SECOND_IN_MILLIS
import vk.vaktija.utils.timeStringToLocalTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Composable
fun CountDownTimer(timeList: List<String>) {
    var timeLeftData by remember { mutableStateOf(TimeLeftData()) }

    DisposableEffect(Unit) {
        val nearestTime = getNearestTimeInMillis(timeList)?.first ?: 0L
        val timer = object : CountDownTimer(nearestTime, ONE_SECOND_IN_MILLIS.toLong()) {
            override fun onTick(p0: Long) {
                val seconds = p0 / ONE_SECOND_IN_MILLIS
                val minutes = seconds / 60
                val hours = minutes / 60

                val minutesFormatted = minutes % 60
                val secondsFormatted = seconds % 60

                val secondsAsString = formatTime(secondsFormatted)
                val minutesAsString = formatTime(minutesFormatted)
                val hoursAsString = formatTime(hours)

                timeLeftData = TimeLeftData(hoursAsString, minutesAsString, secondsAsString)
            }

            override fun onFinish() {
                // nothing
            }
        }.start()

        onDispose {
            timer.cancel()
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
        val prayerTime = timeStringToLocalTime(it)
        val dateTimeObj = LocalDateTime.of(currentDate, prayerTime)

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
