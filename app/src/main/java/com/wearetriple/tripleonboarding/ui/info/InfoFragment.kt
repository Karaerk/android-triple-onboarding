package com.wearetriple.tripleonboarding.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val infoTopicAdapter =
        InfoTopicAdapter(arrayListOf()) { infoTopic: InfoTopic ->
            infoTopicClicked(
                infoTopic
            )
        }
    private lateinit var infoViewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
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
        rvInfoTopics.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvInfoTopics.adapter = infoTopicAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        infoViewModel = ViewModelProvider(activityContext).get(InfoViewModel::class.java)

        infoViewModel.infoTopics.observeNonNull(viewLifecycleOwner, this::initRecyclerView)

    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<InfoTopic>) {
        pbActivity.visibility = GONE
        infoTopicAdapter.items.clear()
        infoTopicAdapter.items.addAll(list)
        infoTopicAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun infoTopicClicked(infoTopic: InfoTopic) {
        val dialogBuilder = AlertDialog.Builder(activityContext)

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
