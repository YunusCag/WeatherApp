package com.yunuscagliyan.weatherapp.presentation.detail.viewmodel

import androidx.lifecycle.*
import com.yunuscagliyan.weatherapp.common.Resource
import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.domain.navigation.NavigationParam
import com.yunuscagliyan.weatherapp.domain.use_case.one_call.GetOneCallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var getOneCallUseCase: GetOneCallUseCase,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private var lat: String = ""
    private var lon: String = ""
    private var apiKey: String = ""

    init {
        savedStateHandle.get<String>(NavigationParam.LAT)?.let { lat ->
            this.lat = lat
        }
        savedStateHandle.get<String>(NavigationParam.LON)?.let { lon ->
            this.lon = lon
        }
        savedStateHandle.get<String>(NavigationParam.API_KEY)?.let { apiId ->
            this.apiKey = apiId
        }
        getOneCall()
    }

    private val _weatherResource: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val weatherResource: LiveData<Resource<WeatherResponse>>
        get() = _weatherResource


    private fun getOneCall() = viewModelScope.launch(Dispatchers.IO) {
        getOneCallUseCase(
            lat = lat,
            lon = lon,
            apiKey = apiKey,
            exclude = "weekly",
            units = "metric",
            lang = "tr"
        ).onEach {
            _weatherResource.postValue(it)
        }.launchIn(viewModelScope)
    }


}