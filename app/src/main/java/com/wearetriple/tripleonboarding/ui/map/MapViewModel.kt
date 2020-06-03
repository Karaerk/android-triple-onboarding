package com.wearetriple.tripleonboarding.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val mapLevelLiveData = MutableLiveData<List<MapLevel>>()
    val mapLevels: LiveData<List<MapLevel>> = mapLevelLiveData

    companion object {
        private const val DATABASE_KEY = "map"
    }

    init {
        viewModelScope.launch {
            postLiveData()
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<MapLevel>(DATABASE_KEY)
        mapLevelLiveData.postValue(data)
    }
}