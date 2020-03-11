package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.InfoTopicAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.InfoTopic
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    private val infoTopics = arrayListOf<InfoTopic>()
    private val repository = BaseEntityRepository<InfoTopic>(InfoTopic.DATABASE_KEY)
    private val infoTopicAdapter =
        InfoTopicAdapter(infoTopics) { infoTopic: InfoTopic -> infoTopicClicked(infoTopic) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvInfoTopics.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvInfoTopics.adapter = infoTopicAdapter

        repository.getAll(object : DataCallback<InfoTopic> {
            override fun onCallback(list: ArrayList<InfoTopic>) {
                if (infoTopics.isNotEmpty())
                    infoTopics.clear()

                infoTopics.addAll(list)

                infoTopicAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun infoTopicClicked(infoTopic: InfoTopic) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(infoTopic.content)
            .setCancelable(false)
            .setNegativeButton("Afsluiten") { dialog, id ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setTitle(infoTopic.title)

        alert.show()
    }
}
