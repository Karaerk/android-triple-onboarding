package com.wearetriple.tripleonboarding.ui.video.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Video
import com.wearetriple.tripleonboarding.ui.video.detail.VideoDetailActivity
import com.wearetriple.tripleonboarding.ui.video.detail.VideoDetailViewModel.Companion.CLICKED_VIDEO
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    private val videoAdapter =
        VideoAdapter(
            arrayListOf()
        ) { video: Video ->
            videoClicked(video)
        }
    private lateinit var videoViewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        supportActionBar?.title = getString(R.string.title_video_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvVideo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvVideo.adapter = videoAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        videoViewModel = ViewModelProvider(this@VideoActivity).get(VideoViewModel::class.java)

        videoViewModel.video.observeNonNull(this, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Video>) {
        pbActivity.visibility = View.GONE
        videoAdapter.items.clear()
        videoAdapter.items.addAll(list)
        videoAdapter.notifyDataSetChanged()
    }

    private fun videoClicked(video: Video) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        intent.putExtra(CLICKED_VIDEO, video)
        startActivity(intent)
    }
}
