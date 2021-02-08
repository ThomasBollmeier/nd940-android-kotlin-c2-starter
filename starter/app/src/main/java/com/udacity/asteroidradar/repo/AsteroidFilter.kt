package com.udacity.asteroidradar.repo

import java.util.*

data class TimeInterval(val start: Date?, val end: Date? = null)

interface AsteroidFilter {
    fun getTimeInterval(): TimeInterval
}

enum class AsteroidFilterType {
    WEEK,
    TODAY,
    SAVED
}

object AsteroidFilterFactory {

    fun createFilter(filterType: AsteroidFilterType) =
        when (filterType) {
            AsteroidFilterType.WEEK -> FilterWeek()
            AsteroidFilterType.TODAY -> FilterToday()
            AsteroidFilterType.SAVED -> FilterAll()
        }

    private class FilterWeek() : AsteroidFilter {

        override fun getTimeInterval(): TimeInterval {

            val today = Calendar.getInstance()
            val endDate = Calendar.getInstance()
            endDate.time = today.time
            endDate.add(Calendar.DAY_OF_MONTH, 7)

            return TimeInterval(today.time, endDate.time)
        }

    }

    private class FilterToday() : AsteroidFilter {

        override fun getTimeInterval(): TimeInterval {

            val today = Calendar.getInstance()

            return TimeInterval(today.time, today.time)
        }

    }

    private class FilterAll() : AsteroidFilter {

        override fun getTimeInterval(): TimeInterval = TimeInterval(null, null)

    }
}

