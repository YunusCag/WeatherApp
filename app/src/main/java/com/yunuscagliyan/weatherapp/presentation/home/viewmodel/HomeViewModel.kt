package com.yunuscagliyan.weatherapp.presentation.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    val latitude: MutableLiveData<String> = MutableLiveData()
    val longitude: MutableLiveData<String> = MutableLiveData()
    val apiKey: MutableLiveData<String> = MutableLiveData()


    fun validate(): Boolean {
        return (this.latitude.value != null) && (this.longitude.value != null) && !(this.apiKey.value.isNullOrEmpty())
    }

}