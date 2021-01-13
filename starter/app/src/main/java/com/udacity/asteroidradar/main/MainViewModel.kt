package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidsDb
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

    private val _imageOfDayUrl = MutableLiveData<String>("")
    val imageOfDayUrl : LiveData<String>
        get() = _imageOfDayUrl

    private val _imageOfDayTitle = MutableLiveData<String>("")
    val imageOfDayTitle : LiveData<String>
        get() = _imageOfDayTitle

    private val _asteroids = MutableLiveData<List<Asteroid>>(emptyList())
    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    private val repository = AsteroidRepository(AsteroidsDb.getInstance(application))

    init {
        viewModelScope.launch {

            val imageData = repository.getImageOfDay()
            _imageOfDayUrl.value = imageData.url
            _imageOfDayTitle.value = imageData.title
            _asteroids.value = repository.getAsteroids()

        }
    }

}