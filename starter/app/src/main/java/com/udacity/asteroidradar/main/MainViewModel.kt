package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaApi
import kotlinx.coroutines.runBlocking
import java.util.*

class MainViewModel : ViewModel() {

    init {
        runBlocking {

            val endDate = Calendar.getInstance()
            val startDate = Calendar.getInstance()
            startDate.time = endDate.time
            startDate.add(Calendar.DAY_OF_MONTH, -Constants.DEFAULT_END_DATE_DAYS)

            val asteroids = NasaApi.getAsteroids(startDate.time, endDate.time)
            for (asteroid in asteroids) {
                Log.d("MainViewModel", "Asteroid: $asteroid")
            }
        }
    }

}