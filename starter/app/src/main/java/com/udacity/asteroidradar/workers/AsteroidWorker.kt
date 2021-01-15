package com.udacity.asteroidradar.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repo.AsteroidRepository
import retrofit2.HttpException

class AsteroidWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params)
{
    companion object {
        const val WORK_NAME = "AsteroidWorker"
    }

    override suspend fun doWork(): Result {

        val database = AsteroidsDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }

}