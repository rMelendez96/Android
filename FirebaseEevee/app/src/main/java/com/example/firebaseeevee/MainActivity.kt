package com.example.firebaseeevee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adaptador
    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = Adaptador(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        verDatos_Firebase()


    }

    fun verDatos_Firebase(){
        viewModel.obtenerDatosUsuarios().observe(this, Observer{ respuesta ->

            adapter.setListData(respuesta)
            adapter.notifyDataSetChanged()
        })
    }
}