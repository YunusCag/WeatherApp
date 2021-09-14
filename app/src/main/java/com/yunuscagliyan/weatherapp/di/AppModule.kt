package com.yunuscagliyan.weatherapp.di

import com.yunuscagliyan.weatherapp.data.remote.WeatherApi
import com.yunuscagliyan.weatherapp.data.remote.url.WeatherUrl
import com.yunuscagliyan.weatherapp.data.repository.WeatherRepository
import com.yunuscagliyan.weatherapp.domain.repository.IWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): IWeatherRepository {
        return WeatherRepository(api)
    }
}