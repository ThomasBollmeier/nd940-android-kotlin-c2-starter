package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import java.util.*

class AsteroidApiDataSource {

    private val dataConverter = createNasaApiDataConverter()

    suspend fun fetchAsteroidsByDates(startDate: Date, endDate: Date): List<Asteroid> {

        val startDateStr = dataConverter.formatDate(startDate)
        val endDateStr = dataConverter.formatDate(endDate)

        val jsonObject = NasaApi.getAsteroids(startDateStr, endDateStr)

        return if (jsonObject != null) {
            dataConverter.asteroidsFromJson(jsonObject)
        } else {
            emptyList()
        }
    }

    suspend fun fetchImageOfDay() : ImageData {

        val jsonObject = NasaApi.getAstronomyPicOfDay()

        return if (jsonObject != null) {
            dataConverter.imageDataFromJson(jsonObject)
        } else {
            ImageData()
        }

    }
}