package com.wearetriple.tripleonboarding.ui.hourbook.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.android.synthetic.main.fragment_hour_book_overview.*

class HourBookOverviewFragment : Fragment(R.layout.fragment_hour_book_overview) {

    private val hourBookTopicAdapter =
        HourBookOverviewAdapter { infoTopic: HourBookTopic ->
            hourBookTopicClicked(
                infoTopic
            )
        }
    private lateinit var hourBookOverviewViewModel: HourBookOverviewViewModel

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
        rvHourTopics.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvHourTopics.adapter = hourBookTopicAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        hourBookOverviewViewModel =
            ViewModelProvider(requireActivity()).get(HourBookOverviewViewModel::class.java)

        hourBookOverviewViewModel.hourBookTopics.observeNonNull(
            viewLifecycleOwner,
            this::initRecyclerView
        )
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<HourBookTopic>) {
        pbActivity.visibility = View.GONE
        hourBookTopicAdapter.items = list
        hourBookTopicAdapter.notifyDataSetChanged()
    }


    /**
     * Opens up a detail fragment containing data of clicked hour book topic.
     */
    private fun hourBookTopicClicked(hourBookTopic: HourBookTopic) {
        val action =
            HourBookOverviewFragmentDirections.actionHourBookOverviewFragmentToHourBookDetailFragment(
                hourBookTopic,
                hourBookTopic.title
            )
        findNavController().navigate(action)
    }
}
