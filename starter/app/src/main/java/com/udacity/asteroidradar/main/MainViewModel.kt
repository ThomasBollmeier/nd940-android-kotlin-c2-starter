package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.repo.AsteroidFilterType
import com.udacity.asteroidradar.repo.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModelFactory(
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AsteroidRepository(AsteroidsDatabase.getInstance(application))

    private val _imageOfDayUrl = MutableLiveData<String>("")
    val imageOfDayUrl : LiveData<String>
        get() = _imageOfDayUrl

    private val _imageOfDayTitle = MutableLiveData<String>("")
    val imageOfDayTitle : LiveData<String>
        get() = _imageOfDayTitle

    val asteroids = repository.asteroids

    init {
        viewModelScope.launch {

            val imageData = repository.fetchImageOfDay()
            _imageOfDayUrl.value = imageData.url
            _imageOfDayTitle.value = imageData.title

            repository.refreshAsteroids()
        }
    }

    fun setFilter(filterType: AsteroidFilterType) {
        viewModelScope.launch {
            repository.setFilter(filterType)
        }
    }

}