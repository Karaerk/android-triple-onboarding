package com.wearetriple.tripleonboarding.ui.info

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment(R.layout.fragment_info) {

    private val infoTopicAdapter =
        InfoTopicAdapter { infoTopic: InfoTopic ->
            infoTopicClicked(
                infoTopic
            )
        }
    private lateinit var infoViewModel: InfoViewModel

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
        rvInfoTopics.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvInfoTopics.adapter = infoTopicAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        infoViewModel = ViewModelProvider(requireActivity()).get(InfoViewModel::class.java)

        infoViewModel.infoTopics.observeNonNull(viewLifecycleOwner, this::initRecyclerView)

    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<InfoTopic>) {
        pbActivity.visibility = GONE
        infoTopicAdapter.items = list
        infoTopicAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun infoTopicClicked(infoTopic: InfoTopic) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

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
