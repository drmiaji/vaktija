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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vk.vaktija.R
import vk.vaktija.utils.isCurrentPrayerTime
import vk.vaktija.models.PrayerTimeResponse
import vk.vaktija.models.enums.PrayerTimeIndex
import vk.vaktija.ui.theme.BlueWithOpacity

@Composable
fun HomeScreenView(viewModel: HomeScreenViewModel) {
    val prayerTimes = viewModel.prayerTimes.collectAsState(null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        if (prayerTimes.value == null) {
            CircularProgressIndicator()
        } else {
            val data = prayerTimes.value ?: return

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        LocationContainer(data.location)
                        Spacer(modifier = Modifier.height(20.dp))
                        CountDownTimerView(timeList = data.prayerTimes)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(R.drawable.home_icon),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Datum",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 16.sp
                    )
                    Text(
                        text = data.date[0],
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = data.date[1],
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                PrayerTimeRow("Zora", data, R.drawable.fajr_icon,
                    PrayerTimeIndex.FAJR
                )
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(
                    "Izlazak Sunca",
                    data,
                    R.drawable.sunrise_icon,
                    PrayerTimeIndex.SUNRISE
                )
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow("Podna", data, R.drawable.duhur_icon, PrayerTimeIndex.DUHUR)
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow("Ikindija", data, R.drawable.asr_icon, PrayerTimeIndex.ASR)
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow("Akšam", data, R.drawable.magrib_icon, PrayerTimeIndex.MAGHRIB)
                Divider(modifier = Modifier.padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2F))
                PrayerTimeRow(
                    "Jacija",
                    data,
                    R.drawable.ishaa_icon,
                    PrayerTimeIndex.ISHAA
                )
            }
        }
    }
}

@Composable
fun PrayerTimeRow(
    label: String,
    data: PrayerTimeResponse,
    icon: Int,
    prayerTimeIndex: PrayerTimeIndex
) {
    val currentPrayerTime = data.prayerTimes[prayerTimeIndex.index]
    val nextPrayerTimeIndex = if (prayerTimeIndex == PrayerTimeIndex.ISHAA) {
        0
    } else {
        prayerTimeIndex.index + 1
    }
    val nextPrayerTime = data.prayerTimes[nextPrayerTimeIndex]
    val isIshaa = prayerTimeIndex == PrayerTimeIndex.ISHAA
    val backgroundColor = if (isCurrentPrayerTime(currentPrayerTime, nextPrayerTime, isIshaa)) {
        BlueWithOpacity
    } else {
        Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .width(40.dp)
                    .padding(end = 16.dp),
                painter = painterResource(icon),
                contentDescription = label,
                tint = Color.Unspecified
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.prayerTimes[prayerTimeIndex.index],
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
            SwitchView()
        }
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
            painter = painterResource(R.drawable.location_icon),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            "$location, BiH",
            modifier = Modifier.padding(0.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
        )
    }
}
