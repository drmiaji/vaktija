package vk.vaktija.data.repository

import kotlinx.coroutines.flow.flow
import vk.vaktija.data.models.NetworkResult
import vk.vaktija.data.models.enums.City
import vk.vaktija.data.remote.ApiService

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