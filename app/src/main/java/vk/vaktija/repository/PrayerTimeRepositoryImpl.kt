package vk.vaktija.repository

import kotlinx.coroutines.flow.flow
import vk.vaktija.models.NetworkResult
import vk.vaktija.models.enums.City
import vk.vaktija.network.ApiService

class PrayerTimeRepositoryImpl(
    private val api: ApiService
) : PrayerTimeRepository {

    override fun getPrayerTimeByCity(location: City) = flow {
        try {
            val response = api.getPrayerTime(location.position)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = e.message))
        }
    }
}