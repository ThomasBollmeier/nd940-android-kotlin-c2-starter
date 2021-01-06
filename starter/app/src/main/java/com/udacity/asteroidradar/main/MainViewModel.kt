package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.nasa.NasaApi
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    init {
        runBlocking {
            val asteroids = NasaApi.getAsteroids()
            for (asteroid in asteroids) {
                Log.d("MainViewModel", "Asteroid: $asteroid")
            }
        }
    }

}