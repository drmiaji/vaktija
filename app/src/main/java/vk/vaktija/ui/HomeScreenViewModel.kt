package vk.vaktija.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import vk.vaktija.data.models.NetworkResult
import vk.vaktija.data.models.PrayerTimeResponse
import vk.vaktija.data.models.enums.City
import vk.vaktija.data.repository.PrayerTimeRepository

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