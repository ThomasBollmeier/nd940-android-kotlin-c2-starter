package com.udacity.asteroidradar.repo

import com.udacity.asteroidradar.Asteroid
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

interface NasaApiDataConverter {

    fun formatDate(date: Date) : String

    fun asteroidsFromJson(jsonObject: JSONObject): List<Asteroid>

}

fun createNasaApiDataConverter() : NasaApiDataConverter = NasaApiDataConverterImpl()

const val API_DATE_FORMAT = "yyyy-MM-dd"

class NasaApiDataConverterImpl : NasaApiDataConverter {

    private val dateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())

    override fun formatDate(date: Date): String = dateFormat.format(date)

    override fun asteroidsFromJson(jsonObject: JSONObject): List<Asteroid> {

        val ret = mutableListOf<Asteroid>()

        val nearEarthObjectsJson = jsonObject.getJSONObject("near_earth_objects")
        val formattedDates = nearEarthObjectsJson.keys()

        for (formattedDate in formattedDates) {

            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

            for (i in 0 until dateAsteroidJsonArray.length()) {
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                val id = asteroidJson.getLong("id")
                val codename = asteroidJson.getString("name")
                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                        .getJSONObject("kilometers").getDouble("estimated_diameter_max")

                val closeApproachData = asteroidJson
                        .getJSONArray("close_approach_data").getJSONObject(0)
                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                        .getDouble("kilometers_per_second")
                val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                        .getDouble("astronomical")
                val isPotentiallyHazardous = asteroidJson
                        .getBoolean("is_potentially_hazardous_asteroid")

                val asteroid = Asteroid(id, codename, formattedDate, absoluteMagnitude,
                        estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
                ret.add(asteroid)
            }
        }

        return ret.sortedByDescending { it.closeApproachDate }

    }

}