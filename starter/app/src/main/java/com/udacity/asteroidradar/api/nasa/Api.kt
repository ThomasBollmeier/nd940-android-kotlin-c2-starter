package com.udacity.asteroidradar.api.nasa

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.NumberFormatException


interface NasaService {

    @GET("feed")
    suspend fun getNearEarthObjectData(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String

    ): Response<NearEarthObjectsData>
}

object NasaApi {

    private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

    private val service: NasaService by lazy {

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        retrofit.create(NasaService::class.java)
    }

    suspend fun getAsteroids(): List<Asteroid> {

        val startDate = "2020-12-24"
        val endDate = "2020-12-31"
        val response = service.getNearEarthObjectData(startDate, endDate, API_KEY)

        return if (response.isSuccessful) {
            toAsteroidList(response.body())
        } else {
            emptyList()
        }
    }

    private fun toAsteroidList(neosData: NearEarthObjectsData?): List<Asteroid> {

        return if (neosData != null) {

            val neosPerDate = neosData.near_earth_objects.values
            val asteroidsMap = mutableMapOf<Long, Asteroid>()

            for (neos in neosPerDate) {

                for (neo in neos) {

                    val id = try {
                        neo.id.toLong()
                    } catch (e: NumberFormatException) {
                        continue
                    }

                    if (id in asteroidsMap) {
                        continue
                    }

                    asteroidsMap[id] = toAsteroid(id, neo)
                }
            }

            asteroidsMap.values.toList()

        } else {
            emptyList()
        }
    }

    private fun toAsteroid(id: Long, neo: NearEarthObject): Asteroid {

        val closeApproachData: CloseApproachData? = neo.close_approach_data.getOrNull(0)

        val codename = neo.name
        val closeApproachDate = closeApproachData?.close_approach_date ?: ""
        val absoluteMagnitude = neo.absolute_magnitude_h
        val estimatedDiameter = neo.estimated_diameter.kilometers.estimated_diameter_max
        val relativeVelocity = try {
            closeApproachData?.relative_velocity?.kilometers_per_second?.toDouble()
                    ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }
        val distanceFromEarth = try {
            closeApproachData?.miss_distance?.astronomical?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }
        val isPotentiallyHazardous = neo.is_potentially_hazardous_asteroid

        return Asteroid(
                id,
                codename,
                closeApproachDate,
                absoluteMagnitude,
                estimatedDiameter,
                relativeVelocity,
                distanceFromEarth,
                isPotentiallyHazardous)

    }

}