package com.yunuscagliyan.weatherapp.domain.repository

import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse

interface IWeatherRepository {

    suspend fun getOneCall(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        lang: String,
        apiKey: String,
    ):WeatherResponse
}