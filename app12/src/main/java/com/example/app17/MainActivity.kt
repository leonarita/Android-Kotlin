package com.example.app17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var model: MontandoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.model = ViewModelProvider(this).get(MontandoViewModel::class.java)

        this.model.requestRepositories()

        this.model.getRepositories().observe(this, Observer {
            it.forEach {repository ->
                println(repository.name)
            }
        })
    }
}
