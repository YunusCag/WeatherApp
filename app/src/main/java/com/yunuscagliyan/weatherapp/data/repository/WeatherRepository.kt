package com.yunuscagliyan.weatherapp.data.repository

import com.yunuscagliyan.weatherapp.data.remote.WeatherApi
import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) : IWeatherRepository {
    override suspend fun getOneCall(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        lang: String,
        apiKey: String
    ): WeatherResponse {
        return api.getOneCall(
            lat,
            lon,
            exclude,
            units,
            lang,
            apiKey,
        )
    }

}