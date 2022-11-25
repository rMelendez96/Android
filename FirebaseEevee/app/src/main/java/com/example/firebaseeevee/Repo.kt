package com.example.firebaseeevee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    fun getUserData(): LiveData<MutableList<Usuario>> {
        val mutableData = MutableLiveData<MutableList<Usuario>>()
        FirebaseFirestore.getInstance().collection("Usuarios").get().addOnSuccessListener { resultado ->
            val listaDatos = mutableListOf<Usuario>()
            for(document in resultado){
                val imageURL:String? = document.getString("imageURL")
                val nombre:String? = document.getString("nombre")
                val descripcion:String? = document.getString("descripcion")
                val usuario = Usuario(imageURL, nombre, descripcion)
                listaDatos.add(usuario)
            }

            mutableData.value = listaDatos
        }
        return mutableData
    }
}