package com.udacity.asteroidradar.api.nasa

data class NearEarthObject(
        val absolute_magnitude_h: Double,
        val close_approach_data: List<CloseApproachData>,
        val estimated_diameter: EstimatedDiameter,
        val id: String,
        val is_potentially_hazardous_asteroid: Boolean,
        val is_sentry_object: Boolean,
        val links: Links,
        val name: String,
        val nasa_jpl_url: String,
        val neo_reference_id: String
)