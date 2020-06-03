package com.wearetriple.tripleonboarding.ui.video.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Video
import com.wearetriple.tripleonboarding.ui.helper.MediaControllerHelper
import com.wearetriple.tripleonboarding.ui.video.detail.VideoDetailViewModel.Companion.CLICKED_VIDEO
import kotlinx.android.synthetic.main.activity_full_screen_video.*

class VideoDetailActivity : AppCompatActivity(R.layout.activity_full_screen_video) {

    private lateinit var videoDetailViewModel: VideoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews(video: Video) {
        val videoUri = Uri.parse(video.url)
        videoView.setVideoURI(videoUri)
        val mediaController = MediaControllerHelper(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

        videoView.setOnPreparedListener {
            pbActivity.visibility = View.GONE
        }
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        videoDetailViewModel =
            ViewModelProvider(this@VideoDetailActivity).get(VideoDetailViewModel::class.java)

        videoDetailViewModel.initVideo(intent.getParcelableExtra(CLICKED_VIDEO)!!)

        videoDetailViewModel.video.observeNonNull(this, this::initViews)
    }
}