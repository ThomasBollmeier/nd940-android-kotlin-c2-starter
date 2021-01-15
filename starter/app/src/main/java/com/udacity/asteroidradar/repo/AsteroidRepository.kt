package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AsteroidRepository(private var database: AsteroidsDatabase) {

    private val apiDataSource = AsteroidApiDataSource()
    private val dbDataSource = AsteroidDbDataSource(database.asteroidDao())

    val asteroids = readRecentAsteroids()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val today = Calendar.getInstance()
            val asteroids = apiDataSource.fetchAsteroidsByDates(today.time, today.time)
            dbDataSource.saveAsteroids(asteroids)
        }
    }

    suspend fun fetchImageOfDay() = apiDataSource.fetchImageOfDay()

    private fun readRecentAsteroids(): LiveData<List<Asteroid>> {

        val today = Calendar.getInstance()
        val sevenDaysBefore = Calendar.getInstance()
        sevenDaysBefore.time = today.time
        sevenDaysBefore.add(Calendar.DAY_OF_MONTH, -7)

        return dbDataSource.readAsteroidsByDates(sevenDaysBefore.time, today.time)
    }


}