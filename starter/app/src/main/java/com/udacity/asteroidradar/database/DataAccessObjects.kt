package com.udacity.asteroidradar.database

import androidx.room.*

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg asteroids: AsteroidEntity)

    @Query("""
SELECT 
    *
FROM 
    asteroids 
WHERE 
    close_approach_date >= :startDate AND 
    close_approach_date <= :endDate
    """)
    suspend fun readByDates(startDate: String, endDate: String) : List<AsteroidEntity>

    @Delete
    suspend fun delete(asteroid: AsteroidEntity)

}