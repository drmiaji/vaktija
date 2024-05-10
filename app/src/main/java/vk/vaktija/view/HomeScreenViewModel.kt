package vk.vaktija.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import vk.vaktija.models.NetworkResult
import vk.vaktija.models.PrayerTimeResponse
import vk.vaktija.models.enums.City
import vk.vaktija.repository.PrayerTimeRepository

class HomeScreenViewModel(
    private val prayerTimeRepository: PrayerTimeRepository
) : ViewModel() {
    private val _prayerTimes = MutableSharedFlow<PrayerTimeResponse>()
    val prayerTimes: SharedFlow<PrayerTimeResponse> = _prayerTimes

    init {
        fetchPrayerTimeData()
    }

    private fun fetchPrayerTimeData() {
        viewModelScope.launch {
            prayerTimeRepository.getPrayerTimeByCity(City.TUZLA).collect {
                when (it) {
                    is NetworkResult.Success -> {
                        val data = it.data ?: return@collect
                        _prayerTimes.emit(data)
                    }

                    is NetworkResult.Loading -> {

                    }

                    is NetworkResult.Error -> {

                    }
                }
            }
        }
    }

}