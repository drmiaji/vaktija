package vk.vaktija.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CountDownTimer(timeList: List<String>) {
    val currentTime = Calendar.getInstance().timeInMillis // Get current time in milliseconds
    val nearestTimeMillis = getNearestTimeInMillis(timeList, currentTime)

    // Calculate remaining milliseconds
    val remainingMillis = nearestTimeMillis - currentTime

    // Convert milliseconds to hours, minutes, seconds
    val hours = (remainingMillis / (1000 * 60 * 60)) % 24
    val minutes = (remainingMillis / (1000 * 60)) % 60
    val seconds = (remainingMillis / 1000) % 60

    var isRunning by remember { mutableStateOf(true) } // Flag to control timer

    LaunchedEffect(isRunning) {
        if (isRunning) {
            delay(1000) // Update every second
            isRunning = remainingMillis > 0 // Stop when time reaches
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Time Until Nearest Prayer:", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "%02d".format(hours), // Format hours with zero padding
                style = MaterialTheme.typography.bodyMedium
            )
            Text("h ", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "%02d".format(minutes), // Format minutes with zero padding
                style = MaterialTheme.typography.bodyMedium
            )
            Text("m ", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "%02d".format(seconds), // Format seconds with zero padding
                style = MaterialTheme.typography.bodyMedium
            )
            Text("s", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun getNearestTimeInMillis(timeList: List<String>, currentTimeMillis: Long): Long {
    val timeList = listOf(
        "3:37",
        "6:18",
        "12:51",
        "16:26",
        "19:22",
        "20:50"
    )

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault()) // Format for parsing time strings
    val sortedTimes = timeList.sortedBy { timeFormatter.parse(it).time } // Sort times in ascending order

    // Find the first time after the current time
    val index = sortedTimes.indexOfFirst { timeFormatter.parse(it).time > currentTimeMillis }

    // If no time is after current time, return first time in the list
    return if (index == -1) {
        timeFormatter.parse(sortedTimes[0]).time
    } else {
        timeFormatter.parse(sortedTimes[index]).time
    }
}
