package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import java.util.*

class AsteroidDbDataSource(private val dao: AsteroidDao) {

    private val converter = createAsteroidsDbDataConverter()

    fun readAsteroidsByDates(start: Date, end: Date): LiveData<List<Asteroid>> {

        val startDate = converter.formatDate(start)
        val endDate = converter.formatDate(end)

        return Transformations.map(dao.readByDates(startDate, endDate)) { asteroidEntities ->
            asteroidEntities.map { converter.fromEntity(it) }
        }
    }

    suspend fun saveAsteroids(asteroids: List<Asteroid>) {

        val asteroidEntities = asteroids.map { converter.toEntity(it) }.toTypedArray()
        dao.insert(*asteroidEntities)
    }

}