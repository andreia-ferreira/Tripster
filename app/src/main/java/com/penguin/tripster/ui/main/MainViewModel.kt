package com.penguin.tripster.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.penguin.tripster.database.PlacesDao
import com.penguin.tripster.database.TripsterDatabase
import com.penguin.tripster.model.PlaceOfInterest
import com.penguin.tripster.network.RetrofitHelper
import com.penguin.tripster.repository.PlacesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val mApplication: Application) : AndroidViewModel(mApplication) {
    private val retrofitHelper: RetrofitHelper = RetrofitHelper
    private val dao: PlacesDao = TripsterDatabase.getDatabase(mApplication).placesDao()
    private val repository: PlacesRepository by lazy { PlacesRepository.getInstance(mApplication, dao, retrofitHelper) }
    val listDisplayPlaces: LiveData<List<PlaceOfInterest>> = repository.listToDisplay
    val isError = repository.reposErrors
    var job: Job? = null
    val isLoading = MutableLiveData(false)
    fun showLoading() { isLoading.value = true }
    fun hideLoading() { isLoading.value = false }

    fun refreshPlaces(reset: Boolean) {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            val completed = async { repository.refreshPlaces(reset, "41.199474,-8.332073", 30000, "tourist_attraction") }

            if (completed.await()) {
                repository.getStoredPlaces()
            }

        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}