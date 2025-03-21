package vk.vaktija.data.repository

import kotlinx.coroutines.flow.Flow
import vk.vaktija.data.models.NetworkResult
import vk.vaktija.data.models.PrayerTimeResponse
import vk.vaktija.data.models.enums.City

interface PrayerTimeRepository {

    fun getPrayerTimeByCity(location: City): Flow<NetworkResult<PrayerTimeResponse>>
}