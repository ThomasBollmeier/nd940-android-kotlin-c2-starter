package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NasaService {

    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObjectData(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String

    ): Response<String>
}

object NasaApi {

    private const val BASE_URL = Constants.BASE_URL

    private val service: NasaService by lazy {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        retrofit.create(NasaService::class.java)
    }

    private val dataConverter = createNasaApiDataConverter()

    suspend fun getAsteroids(startDate: Date, endDate: Date): List<Asteroid> {

        val startDateString = dataConverter.dateString(startDate)
        val endDateString = dataConverter.dateString(endDate)

        return try {
            val response = service.getNearEarthObjectData(startDateString, endDateString, API_KEY)
            if (response.isSuccessful) {
                val jsonString = response.body()!!
                dataConverter.asteroidsFromJson(JSONObject(jsonString))
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}