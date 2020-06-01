package com.wearetriple.tripleonboarding.ui.video.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Video
import com.wearetriple.tripleonboarding.ui.video.detail.VideoDetailActivity
import com.wearetriple.tripleonboarding.ui.video.detail.VideoDetailViewModel.Companion.CLICKED_VIDEO
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val videoAdapter =
        VideoAdapter(
            arrayListOf()
        ) { video: Video ->
            videoClicked(video)
        }
    private lateinit var videoViewModel: VideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        rvVideo.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvVideo.adapter = videoAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        videoViewModel = ViewModelProvider(activityContext).get(VideoViewModel::class.java)

        videoViewModel.video.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
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
        val intent = Intent(activityContext, VideoDetailActivity::class.java)
        intent.putExtra(CLICKED_VIDEO, video)
        startActivity(intent)
    }
}
