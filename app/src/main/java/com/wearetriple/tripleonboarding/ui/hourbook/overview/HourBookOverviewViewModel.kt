package com.wearetriple.tripleonboarding.ui.hourbook.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.coroutines.launch

class HourBookOverviewViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val hourBookTopicsLiveData = MutableLiveData<List<HourBookTopic>>()
    val hourBookTopics: LiveData<List<HourBookTopic>> = hourBookTopicsLiveData

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
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<HourBookTopic>(DATABASE_KEY)
        hourBookTopicsLiveData.postValue(data)
    }
}