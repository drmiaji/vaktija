package vk.vaktija

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

fun timeStringToLocalTime(time: String): LocalTime {
    val formatTime = if (time.count() == 4) "0$time" else time
    return LocalTime.parse(formatTime)
}

fun isCurrentPrayerTime(currentPrayerTime: String, nextPrayerTime: String, isIshaa: Boolean = false): Boolean {
    val currentTime = Calendar.getInstance().timeInMillis
    val currentDate = LocalDateTime.now().toLocalDate()
    val prayerTime = LocalDateTime.of(currentDate, timeStringToLocalTime(currentPrayerTime))
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
    val nextTimeForPrayer = LocalDateTime.of(currentDate, timeStringToLocalTime(nextPrayerTime))
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()
    val midNight = LocalDateTime.of(currentDate, timeStringToLocalTime("23:59"))
        .atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli()

    val nextTime = if (isIshaa) midNight else nextTimeForPrayer

    return (prayerTime <= currentTime) && (currentTime < nextTime)
}