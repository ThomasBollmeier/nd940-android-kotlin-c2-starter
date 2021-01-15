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
WHERE 
    close_approach_date >= :startDate AND 
    close_approach_date <= :endDate
    """)
    fun readByDates(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Delete
    fun delete(asteroid: AsteroidEntity)

}