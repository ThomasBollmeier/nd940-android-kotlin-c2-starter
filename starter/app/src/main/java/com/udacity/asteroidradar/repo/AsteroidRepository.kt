package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import java.util.*

interface AsteroidDataSource {

    suspend fun getAsteroidsByDate(startDate: Date, endDate: Date) : List<Asteroid>

}

object AsteroidRepository {

    private val apiDataSource: AsteroidDataSource = AsteroidApiDataSource()
    private val activeDataSource = apiDataSource

    suspend fun getAsteroids() : List<Asteroid> {

        val endDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = endDate.time
        startDate.add(Calendar.DAY_OF_MONTH, -Constants.DEFAULT_END_DATE_DAYS)

        return activeDataSource.getAsteroidsByDate(startDate.time, endDate.time)

    }

}