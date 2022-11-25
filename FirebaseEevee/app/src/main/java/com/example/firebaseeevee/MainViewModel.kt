package com.example.firebaseeevee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val repo = Repo()
    fun obtenerDatosUsuarios(): LiveData<MutableList<Usuario>> {
        val mutableData = MutableLiveData<MutableList<Usuario>>()
        repo.getUserData().observeForever { userList->
            mutableData.value = userList
        }
        return mutableData
    }
}