package com.wearetriple.tripleonboarding.ui.video.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Video
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val videoLiveData = MutableLiveData<List<Video>>()
    val video: LiveData<List<Video>> = videoLiveData

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
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<Video>(DATABASE_KEY)
        videoLiveData.postValue(data)
    }
}