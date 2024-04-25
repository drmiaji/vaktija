package vk.vaktija.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vk.vaktija.R

@Composable
fun HomeScreenView(viewModel: HomeScreenViewModel) {
    val prayerTimes = viewModel.prayerTimes.collectAsState(null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (prayerTimes.value == null) {
            CircularProgressIndicator()
        } else {
            val data = prayerTimes.value ?: return
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        LocationContainer(data.location)
                        CountDownTimer(timeList = data.prayerTimes)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(R.drawable.mosque),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                // Text(text = "Date: ${data.date[0]} (${data.date[1]})", fontSize = 16.sp)  // Consider adding date if relevant
                Spacer(modifier = Modifier.height(16.dp))

                // Prayer time rows with visual separators
                PrayerTimeRow(label = "Zora", time = data.prayerTimes[0])
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Izlazak Sunca", time = data.prayerTimes[1])
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Podne", time = data.prayerTimes[2])
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Ikindija", time = data.prayerTimes[3])
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(label = "Akšam", time = data.prayerTimes[4])
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(
                    label = "Jacija",
                    time = data.prayerTimes[5]
                )
            }
        }
    }
}

@Composable
fun PrayerTimeRow(label: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, fontSize = 16.sp)
        Text(text = time, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, fontSize = 16.sp)
    }
}

@Composable
fun LocationContainer(location: String) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.height(26.dp),
            painter = painterResource(R.drawable.location),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            "$location, BiH",
            modifier = Modifier.padding(0.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Light
        )
    }
}
