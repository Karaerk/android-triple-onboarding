package com.wearetriple.tripleonboarding.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.coroutines.launch


class InfoViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val infoTopicLiveData = MutableLiveData<List<InfoTopic>>()
    val infoTopics: LiveData<List<InfoTopic>> = infoTopicLiveData

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
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<InfoTopic>(DATABASE_KEY)
        infoTopicLiveData.postValue(data)
    }
}