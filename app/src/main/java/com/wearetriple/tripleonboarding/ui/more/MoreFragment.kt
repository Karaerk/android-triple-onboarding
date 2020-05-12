package com.wearetriple.tripleonboarding.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import kotlinx.android.synthetic.main.fragment_info.pbActivity
import kotlinx.android.synthetic.main.fragment_more.*


class MoreFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val itemsAdapter =
        MoreAdapter(arrayListOf()) { item ->
            menuItemClicked(item)
        }
    private lateinit var moreViewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
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
        rvItems.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvItems.adapter = itemsAdapter
        rvItems.addItemDecoration(
            DividerItemDecoration(
                activityContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        moreViewModel = ViewModelProvider(activityContext).get(MoreViewModel::class.java)

        moreViewModel.items.observeNonNull(viewLifecycleOwner, this::initRecyclerView)

    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<String>) {
        pbActivity.visibility = GONE
        itemsAdapter.items.clear()
        itemsAdapter.items.addAll(list)
        itemsAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun menuItemClicked(destination: String) {
        when (destination) {
            "FAQ" -> findNavController().navigate(R.id.action_moreFragment_to_faqFragment)
            "Afdelingen" -> findNavController().navigate(R.id.action_moreFragment_to_departmentOverviewFragment)
            "Urenboek" -> findNavController().navigate(R.id.action_moreFragment_to_hourBookOverviewFragment)
            "Video's" -> findNavController().navigate(R.id.action_moreFragment_to_videoFragment)
            else -> return
        }

    }
}
