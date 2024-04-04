package vk.vaktija.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreenView(viewModel: HomeScreenViewModel) {
    val prayerTimes = viewModel.prayerTimes.collectAsState(null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background), // Set background color
        contentAlignment = Alignment.Center
    ) {
        if (prayerTimes.value == null) {
            // Show loading indicator
            CircularProgressIndicator()
        } else {
            val data = prayerTimes.value ?: return
            Log.e("ALO BRE", data.toString())
            Column(modifier = Modifier.padding(16.dp)) {
                CountDownTimer(timeList = data.prayerTimes)
                Text(
                    text = data.location,
                    style = MaterialTheme.typography.bodyLarge.copy( // Use h6 for title
                        color = MaterialTheme.colorScheme.primary // Use primary color for title
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Text(text = "Date: ${data.date[0]} (${data.date[1]})", fontSize = 16.sp)  // Consider adding date if relevant
                Spacer(modifier = Modifier.height(16.dp))

                // Prayer time rows with visual separators
                PrayerTimeRow(label = "Zora", time = data.prayerTimes[0])
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2F)) // Semi-transparent divider
                PrayerTimeRow(label = "Izlazak Sunca", time = data.prayerTimes[1])
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Podne", time = data.prayerTimes[2])
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Ikindija", time = data.prayerTimes[3])
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Akšam", time = data.prayerTimes[4])
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Jacija", time = data.prayerTimes[5]) // Assuming Imsak is the last prayer time
            }
        }
    }
}

@Composable
fun PrayerTimeRow(label: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Add some vertical padding
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = time, style = MaterialTheme.typography.bodyMedium)
    }
}
