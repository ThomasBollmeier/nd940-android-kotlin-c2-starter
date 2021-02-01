package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDao
import java.util.*

class AsteroidDbDataSource(private val dao: AsteroidDao) {

    private val converter = createAsteroidsDbDataConverter()

    fun readAsteroids(filter: AsteroidFilter): List<Asteroid> {

        val (start, end) = filter.getTimeInterval()

        val entities = if (start != null) {
            val startDate = converter.formatDate(start)
            if (end != null) {
                val endDate = converter.formatDate(end)
                dao.readByDates(startDate, endDate)
            } else {
                dao.readByDate(startDate)
            }
        } else {
            dao.readAll()
        }

        return entities.map { converter.fromEntity(it) }

    }

    fun deleteBeforeDate(date: Date) {

        val entities = dao.readBeforeDate(converter.formatDate(date))
        dao.delete(entities)
    }

    fun saveAsteroids(asteroids: List<Asteroid>) {

        val asteroidEntities = asteroids.map { converter.toEntity(it) }.toTypedArray()
        dao.insert(*asteroidEntities)
    }

}