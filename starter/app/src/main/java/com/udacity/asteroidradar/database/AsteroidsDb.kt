package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidsDb : RoomDatabase() {

    companion object {

        private lateinit var db: AsteroidsDb

        fun getInstance(context: Context): AsteroidsDb {

            synchronized(AsteroidsDb::class.java) {
                if (!::db.isInitialized) {
                    db = Room.databaseBuilder(context.applicationContext,
                            AsteroidsDb::class.java,
                            "asteroids").build()
                }
            }

            return db

        }

    }

    abstract fun asteroidDao(): AsteroidDao

}
