package com.yunuscagliyan.weatherapp.domain.use_case.one_call

import com.yunuscagliyan.weatherapp.common.Resource
import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOneCallUseCase @Inject constructor(
    private val repository: IWeatherRepository,
) {
    operator fun invoke(
        lat: String,
        lon: String,
        exclude: String,
        units: String,
        lang: String,
        apiKey: String,
    ): Flow<Resource<WeatherResponse>> = flow{
        emit(Resource.Loading<WeatherResponse>())
        try{
            val response=repository.getOneCall(lat, lon, exclude, units, lang, apiKey)
            emit(Resource.Success(response))
        }catch (e:HttpException){
            emit(Resource.Error<WeatherResponse>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e:IOException){
            emit(Resource.Error<WeatherResponse>("Couldn't reach server. Check your internet connection."))

        }
    }
}