package vk.vaktija.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import vk.vaktija.data.models.PrayerTimeResponse

interface ApiService {

    @GET("{locationId}")
    suspend fun getPrayerTime(@Path("locationId") locationId: Int): PrayerTimeResponse
}