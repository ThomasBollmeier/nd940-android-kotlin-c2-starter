package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidEntity
import java.text.SimpleDateFormat
import java.util.*

interface AsteroidsDbDataConverter {

    fun formatDate(date: Date): String

    fun fromEntity(entity: AsteroidEntity): Asteroid

    fun toEntity(asteroid: Asteroid): AsteroidEntity

}

const val SQL_DATE_FORMAT = "yyyy-MM-dd"

class AsteroidsDbDataConverterImpl : AsteroidsDbDataConverter {

    private val dateFormat = SimpleDateFormat(SQL_DATE_FORMAT, Locale.getDefault())

    override fun fromEntity(entity: AsteroidEntity) =
            Asteroid(
                    id = entity.id,
                    codename = entity.codename,
                    closeApproachDate = entity.closeApproachDate,
                    absoluteMagnitude = entity.absoluteMagnitude,
                    estimatedDiameter = entity.estimatedDiameter,
                    relativeVelocity = entity.relativeVelocity,
                    distanceFromEarth = entity.distanceFromEarth,
                    isPotentiallyHazardous = entity.isPotentiallyHazardous
            )

    override fun toEntity(asteroid: Asteroid) =
            AsteroidEntity(
                    id = asteroid.id,
                    codename = asteroid.codename,
                    closeApproachDate = asteroid.closeApproachDate,
                    absoluteMagnitude = asteroid.absoluteMagnitude,
                    estimatedDiameter = asteroid.estimatedDiameter,
                    relativeVelocity = asteroid.relativeVelocity,
                    distanceFromEarth = asteroid.distanceFromEarth,
                    isPotentiallyHazardous = asteroid.isPotentiallyHazardous
            )

    override fun formatDate(date: Date): String = dateFormat.format(date)
}

fun createAsteroidsDbDataConverter(): AsteroidsDbDataConverter = AsteroidsDbDataConverterImpl()