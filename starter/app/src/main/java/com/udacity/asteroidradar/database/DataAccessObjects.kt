package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroids: AsteroidEntity)

    @Query("""
SELECT 
    *
FROM 
    asteroids 
ORDER BY
    close_approach_date DESC
    """)
    fun readAll() : List<AsteroidEntity>

    @Query("""
SELECT 
    *
FROM 
    asteroids 
WHERE 
    close_approach_date = :date
    """)
    fun readByDate(date: String) : List<AsteroidEntity>

    @Query("""
SELECT 
    *
FROM 
    asteroids 
WHERE 
    close_approach_date >= :startDate AND 
    close_approach_date <= :endDate
ORDER BY
    close_approach_date DESC
    """)
    fun readByDates(startDate: String, endDate: String) : List<AsteroidEntity>

    @Delete
    fun delete(asteroid: AsteroidEntity)

}