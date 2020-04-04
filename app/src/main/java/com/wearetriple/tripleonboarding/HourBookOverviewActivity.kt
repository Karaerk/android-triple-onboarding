package com.wearetriple.tripleonboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.HourBookTopicAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_hour_book_overview.*

class HourBookOverviewActivity : AppCompatActivity() {

    private val hourBookTopics = arrayListOf<HourBookTopic>()
    private val repository = BaseEntityRepository<HourBookTopic>(HourBookTopic.DATABASE_KEY)
    private val hourBookTopicAdapter =
        HourBookTopicAdapter(hourBookTopics) { infoTopic: HourBookTopic ->
            hourBookTopicClicked(
                infoTopic
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_book_overview)
        supportActionBar?.title = getString(R.string.title_hours_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvHourTopics.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHourTopics.adapter = hourBookTopicAdapter

        repository.getAll(object : DataCallback<HourBookTopic> {
            override fun onCallback(list: ArrayList<HourBookTopic>) {
                if (hourBookTopics.isNotEmpty())
                    hourBookTopics.clear()

                hourBookTopics.addAll(list)

                hourBookTopicAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * Opens up a detail activity containing data of clicked hour book topic.
     */
    private fun hourBookTopicClicked(hourBookTopic: HourBookTopic) {
        val intent = Intent(this@HourBookOverviewActivity, HourBookDetailActivity::class.java)

        intent.putExtra(CLICKED_HOUR_BOOK_TOPIC, hourBookTopic)
        startActivity(intent)
    }
}
