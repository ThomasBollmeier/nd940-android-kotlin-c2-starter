package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AsteroidRepository(private var database: AsteroidsDatabase) {

    private val apiDataSource = AsteroidApiDataSource()
    private val dbDataSource = AsteroidDbDataSource(database.asteroidDao())
    private var filter = AsteroidFilterFactory.createFilter(AsteroidFilterType.WEEK)

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

            val today = Calendar.getInstance()

            fetchAndSaveNextSevenDaysData(today = today.time)
            deleteOldAsteroids(today = today.time)

            setFilter(AsteroidFilterType.WEEK)
        }
    }

    private fun deleteOldAsteroids(today: Date) {

        val deleteBefore = Calendar.getInstance()
        deleteBefore.time = today
        deleteBefore.add(Calendar.DAY_OF_MONTH, -7)

        dbDataSource.deleteBeforeDate(deleteBefore.time)
    }

    private suspend fun fetchAndSaveNextSevenDaysData(today: Date) {

        val endDate = Calendar.getInstance()
        endDate.time = today
        endDate.add(Calendar.DAY_OF_MONTH, 7)
        val asteroids = apiDataSource.fetchAsteroidsByDates(today, endDate.time)
        dbDataSource.saveAsteroids(asteroids)
    }

    suspend fun fetchImageOfDay() = apiDataSource.fetchImageOfDay()

    suspend fun setFilter(filterType: AsteroidFilterType) {
        filter = AsteroidFilterFactory.createFilter(filterType)
        withContext(Dispatchers.IO) {
            _asteroids.postValue(dbDataSource.readAsteroids(filter))
        }
    }

}