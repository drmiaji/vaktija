package vk.vaktija.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun timeStringToLocalTime(time: String): LocalTime {
    val formatTime = if (time.count() == 4) "0$time" else time
    return LocalTime.parse(formatTime)
}

fun isCurrentPrayerTime(
    currentPrayerTime: String,
    nextPrayerTime: String,
    isIshaa: Boolean = false
): Boolean {
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

fun getAlarmTimeInMillis(time: String): Long {
    val hours = time.substring(2).toInt()
    val minutes = time.substringAfter(":").toInt()

    val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hours)
        set(Calendar.MINUTE, minutes)
        set(Calendar.SECOND, 0)
    }

    return calendar.timeInMillis - TimeUnit.MINUTES.toMillis(15)
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

fun formatTime(time: Long): String = if (time < 10) {
    "0${time}"
} else {
    time.toString()
}