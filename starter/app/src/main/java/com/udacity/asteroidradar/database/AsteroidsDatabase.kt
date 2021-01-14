package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {

    companion object {

        private lateinit var database: AsteroidsDatabase

        fun getInstance(context: Context): AsteroidsDatabase {

            synchronized(AsteroidsDatabase::class.java) {
                if (!::database.isInitialized) {
                    database = Room.databaseBuilder(context.applicationContext,
                            AsteroidsDatabase::class.java,
                            "asteroids").build()
                }
            }

            return database

        }

    }

    abstract fun asteroidDao(): AsteroidDao

}
