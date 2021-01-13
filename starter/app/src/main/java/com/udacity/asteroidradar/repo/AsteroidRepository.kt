package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidsDb
import java.util.*

class AsteroidRepository(private var database: AsteroidsDb) {

    private val apiDataSource = AsteroidApiDataSource()

    suspend fun getAsteroids() : List<Asteroid> {

        val endDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = endDate.time
        startDate.add(Calendar.DAY_OF_MONTH, -Constants.DEFAULT_END_DATE_DAYS)

        return apiDataSource.getAsteroidsByDate(startDate.time, endDate.time)

    }

    suspend fun getImageOfDay() = apiDataSource.getImageOfDay()

}