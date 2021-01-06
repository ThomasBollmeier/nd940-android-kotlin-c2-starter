package com.udacity.asteroidradar.api.nasa

data class NearEarthObjectsData(
    val element_count: Int,
    val links: Links,
    val near_earth_objects: Map<String, List<NearEarthObject>>
)