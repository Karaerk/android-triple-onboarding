package com.wearetriple.tripleonboarding.ui.video.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wearetriple.tripleonboarding.model.Video

class VideoDetailViewModel : ViewModel() {
    val video = MutableLiveData<Video>()

    companion object {
        const val CLICKED_VIDEO = "CLICKED_VIDEO"
    }

    /**
     * Keeps track of currently selected video for the detail view.
     */
    fun initVideo(video: Video) {
        this.video.value = video
    }
}