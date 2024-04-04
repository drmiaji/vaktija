package vk.vaktija.network

import retrofit2.http.GET
import retrofit2.http.Path
import vk.vaktija.models.PrayerTimeResponse

interface ApiService {

    @GET("{locationId}")
    suspend fun getPrayerTime(@Path("locationId") locationId: Int): PrayerTimeResponse
}