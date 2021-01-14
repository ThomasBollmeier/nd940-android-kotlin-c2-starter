package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import java.util.*

class AsteroidDbDataSource(private val dao: AsteroidDao) {

    private val converter = createAsteroidsDbDataConverter()

    suspend fun readAsteroidsByDates(start: Date, end: Date): List<Asteroid> {

        val startDate = converter.formatDate(start)
        val endDate = converter.formatDate(end)

        return dao.readByDates(startDate, endDate).map { converter.fromEntity(it) }
    }

    suspend fun saveAsteroids(asteroids: List<Asteroid>) {

        val asteroidEntities = asteroids.map { converter.toEntity(it) }.toTypedArray()

        dao.insert(*asteroidEntities)
    }

}