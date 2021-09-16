package com.yunuscagliyan.weatherapp.domain.use_case.one_call

import android.content.Context
import com.yunuscagliyan.weatherapp.R
import com.yunuscagliyan.weatherapp.common.Resource
import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.domain.repository.IWeatherRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOneCallUseCase @Inject constructor(
    private val repository: IWeatherRepository,
    @ApplicationContext private val context: Context,
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
            emit(Resource.Error<WeatherResponse>(e.localizedMessage ?: context.getString(R.string.un_expected_error_text)))
        }catch (e:IOException){
            emit(Resource.Error<WeatherResponse>(context.getString(R.string.no_internet_connection_text)))

        }
    }
}