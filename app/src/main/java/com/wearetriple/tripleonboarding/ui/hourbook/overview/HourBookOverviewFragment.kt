package com.wearetriple.tripleonboarding.ui.hourbook.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.android.synthetic.main.fragment_hour_book_overview.*

class HourBookOverviewFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val hourBookTopicAdapter =
        HourBookOverviewAdapter(
            arrayListOf()
        ) { infoTopic: HourBookTopic ->
            hourBookTopicClicked(
                infoTopic
            )
        }
    private lateinit var hourBookOverviewViewModel: HourBookOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_book_overview, container, false)
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
        rvHourTopics.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvHourTopics.adapter = hourBookTopicAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        hourBookOverviewViewModel =
            ViewModelProvider(activityContext).get(HourBookOverviewViewModel::class.java)

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
        hourBookTopicAdapter.items.clear()
        hourBookTopicAdapter.items.addAll(list)
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
