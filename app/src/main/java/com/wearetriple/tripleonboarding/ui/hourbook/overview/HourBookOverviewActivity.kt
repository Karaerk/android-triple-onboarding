package com.wearetriple.tripleonboarding.ui.hourbook.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.ui.hourbook.detail.HourBookDetailActivity
import com.wearetriple.tripleonboarding.ui.hourbook.detail.HourBookDetailViewModel
import kotlinx.android.synthetic.main.activity_hour_book_overview.*

class HourBookOverviewActivity : AppCompatActivity() {

    private val hourBookTopicAdapter =
        HourBookOverviewAdapter(
            arrayListOf()
        ) { infoTopic: HourBookTopic ->
            hourBookTopicClicked(
                infoTopic
            )
        }
    private lateinit var hourBookOverviewViewModel: HourBookOverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_book_overview)
        supportActionBar?.title = getString(R.string.title_hours_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvHourTopics.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHourTopics.adapter = hourBookTopicAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        hourBookOverviewViewModel =
            ViewModelProvider(this@HourBookOverviewActivity).get(HourBookOverviewViewModel::class.java)

        hourBookOverviewViewModel.hourBookTopics.observeNonNull(this, this::initRecyclerView)
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
     * Opens up a detail activity containing data of clicked hour book topic.
     */
    private fun hourBookTopicClicked(hourBookTopic: HourBookTopic) {
        val intent = Intent(this@HourBookOverviewActivity, HourBookDetailActivity::class.java)

        intent.putExtra(HourBookDetailViewModel.CLICKED_HOUR_BOOK_TOPIC, hourBookTopic)
        startActivity(intent)
    }
}
