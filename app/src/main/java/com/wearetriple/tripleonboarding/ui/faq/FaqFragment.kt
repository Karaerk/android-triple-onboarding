package com.wearetriple.tripleonboarding.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.android.synthetic.main.fragment_faq.*

class FaqFragment : Fragment(R.layout.fragment_faq) {

    private lateinit var activityContext: AppCompatActivity
    private val faqAdapter = FaqAdapter()
    private lateinit var faqViewModel: FaqViewModel

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
        rvFaq.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvFaq.adapter = faqAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        faqViewModel = ViewModelProvider(activityContext).get(FaqViewModel::class.java)

        faqViewModel.faq.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Faq>) {
        pbActivity.visibility = View.GONE
        faqAdapter.items = list
        faqAdapter.notifyDataSetChanged()
    }
}
