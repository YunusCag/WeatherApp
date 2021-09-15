package com.yunuscagliyan.weatherapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

):ViewModel() {

    val latitude:MutableLiveData<String> =MutableLiveData()
    val longitude:MutableLiveData<String> =MutableLiveData()

}