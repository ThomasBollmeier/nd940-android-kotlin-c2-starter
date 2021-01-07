package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.repo.AsteroidRepository
import kotlinx.coroutines.runBlocking
import java.util.*

class MainViewModel : ViewModel() {

    init {
        runBlocking {

            val asteroids = AsteroidRepository.getAsteroids()
            for (asteroid in asteroids) {
                Log.d("MainViewModel", "Asteroid: $asteroid")
            }
        }
    }

}