package com.wearetriple.tripleonboarding.ui.hourbook.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HourBookOverviewViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val hourBookTopicsLiveData = MediatorLiveData<List<HourBookTopic>>()
    val hourBookTopics = hourBookTopicsLiveData

    companion object {
        private const val DATABASE_KEY = "hours"
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
        val data = repository.getAllFromTable<HourBookTopic>(DATABASE_KEY)
        hourBookTopicsLiveData.postValue(data)
    }
}