package com.wearetriple.tripleonboarding.ui.video.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val videoLiveData = MediatorLiveData<List<Video>>()
    val video = videoLiveData

    companion object {
        private const val DATABASE_KEY = "video"
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
        val data = repository.getAllFromTable<Video>(DATABASE_KEY)
        videoLiveData.postValue(data)
    }
}