package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import java.util.*

class AsteroidApiDataSource : AsteroidDataSource {

    private val dataConverter = createNasaApiDataConverter()

    override suspend fun getAsteroidsByDate(startDate: Date, endDate: Date): List<Asteroid> {

        val startDateStr = dataConverter.formatDate(startDate)
        val endDateStr = dataConverter.formatDate(endDate)

        val jsonObject = NasaApi.getAsteroids(startDateStr, endDateStr)

        return if (jsonObject != null) {
            dataConverter.asteroidsFromJson(jsonObject)
        } else {
            emptyList()
        }
    }
}