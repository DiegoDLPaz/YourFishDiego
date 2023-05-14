package com.example.prueba

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _ip = MutableLiveData<String?>()
    val ip: LiveData<String?> get() = _ip

    fun setIp(ip: String?){
        _ip.value = ip
    }

}