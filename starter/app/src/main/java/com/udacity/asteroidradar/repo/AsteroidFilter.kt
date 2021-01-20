package com.udacity.asteroidradar.repo

import java.util.*

data class TimeInterval(val start: Date?, val end: Date? = null)

interface AsteroidFilter {
    fun getTimeInterval(): TimeInterval
}

class AsteroidFilterWeek() : AsteroidFilter {

    override fun getTimeInterval(): TimeInterval {

        val today = Calendar.getInstance()
        val sevenDaysBefore = Calendar.getInstance()
        sevenDaysBefore.time = today.time
        sevenDaysBefore.add(Calendar.DAY_OF_MONTH, -7)

        return TimeInterval(sevenDaysBefore.time, today.time)
    }

}