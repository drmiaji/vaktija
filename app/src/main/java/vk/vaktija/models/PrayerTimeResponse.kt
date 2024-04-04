package vk.vaktija.models

import com.google.gson.annotations.SerializedName

data class PrayerTimeResponse(
    val id: Int,
    @SerializedName("lokacija")
    val location: String,
    @SerializedName("datum")
    val date: List<String>,
    @SerializedName("vakat")
    val prayerTimes: List<String>
)