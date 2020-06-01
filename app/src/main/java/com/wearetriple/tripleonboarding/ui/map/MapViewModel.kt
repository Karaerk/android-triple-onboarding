package com.wearetriple.tripleonboarding.ui.map

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val mapLevelLiveData = MediatorLiveData<List<MapLevel>>()
    val mapLevels = mapLevelLiveData

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
    private suspend fun postLiveData() = withContext(Dispatchers.IO) {
        val data = repository.getAllFromTable<MapLevel>(DATABASE_KEY)
        mapLevelLiveData.postValue(data)
    }
}