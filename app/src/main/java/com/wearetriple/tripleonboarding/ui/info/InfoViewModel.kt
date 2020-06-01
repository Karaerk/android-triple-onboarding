package com.wearetriple.tripleonboarding.ui.info

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InfoViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val infoTopicLiveData = MediatorLiveData<List<InfoTopic>>()
    val infoTopics = infoTopicLiveData

    companion object {
        private const val DATABASE_KEY = "info"
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
        val data = repository.getAllFromTable<InfoTopic>(DATABASE_KEY)
        infoTopicLiveData.postValue(data)
    }
}