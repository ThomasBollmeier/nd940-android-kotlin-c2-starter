package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
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

    private val _asteroids = MutableLiveData<List<Asteroid>>(emptyList())
    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    init {

        viewModelScope.launch {
            _asteroids.value = AsteroidRepository.getAsteroids()
            for (asteroid in _asteroids.value!!) {
                Log.d("MainViewModel", "Asteroid: $asteroid")
            }
        }
    }

}