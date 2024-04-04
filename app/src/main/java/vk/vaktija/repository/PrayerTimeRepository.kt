package vk.vaktija.repository

import kotlinx.coroutines.flow.Flow
import vk.vaktija.models.NetworkResult
import vk.vaktija.models.PrayerTimeResponse
import vk.vaktija.models.enums.City

interface PrayerTimeRepository {

    fun getPrayerTimeByCity(location: City): Flow<NetworkResult<PrayerTimeResponse>>
}