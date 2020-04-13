package com.wearetriple.tripleonboarding.ui.info

import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {

    private val infoTopicAdapter =
        InfoTopicAdapter(arrayListOf()) { infoTopic: InfoTopic ->
            infoTopicClicked(
                infoTopic
            )
        }
    private lateinit var infoViewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar?.title = getString(R.string.title_info_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvInfoTopics.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvInfoTopics.adapter = infoTopicAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        infoViewModel = ViewModelProvider(this@InfoActivity).get(InfoViewModel::class.java)

        infoViewModel.infoTopics.observe(this, Observer { list ->
                pbActivity.visibility = GONE
                infoTopicAdapter.items.clear()
                infoTopicAdapter.items.addAll(list)
                infoTopicAdapter.notifyDataSetChanged()
        })
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun infoTopicClicked(infoTopic: InfoTopic) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(infoTopic.content)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.btn_close_dialog)) { dialog, _ ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setTitle(infoTopic.title)

        alert.show()
    }
}
