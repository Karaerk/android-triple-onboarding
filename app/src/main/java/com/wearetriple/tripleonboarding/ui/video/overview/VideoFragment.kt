package com.wearetriple.tripleonboarding.ui.video.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
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


class VideoFragment : Fragment(R.layout.fragment_video) {

    private val videoAdapter =
        VideoAdapter { video: Video ->
            videoClicked(video)
        }
    private lateinit var videoViewModel: VideoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        rvVideo.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvVideo.adapter = videoAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        videoViewModel = ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

        videoViewModel.video.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Video>) {
        pbActivity.visibility = View.GONE
        videoAdapter.items = list
        videoAdapter.notifyDataSetChanged()
    }

    private fun videoClicked(video: Video) {
        val intent = Intent(requireActivity(), VideoDetailActivity::class.java)
        intent.putExtra(CLICKED_VIDEO, video)
        startActivity(intent)
    }
}
