package com.wearetriple.tripleonboarding.ui.faq

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Faq
import kotlinx.android.synthetic.main.activity_faq.*

class FaqActivity : AppCompatActivity() {

    private val faqAdapter = FaqAdapter(arrayListOf())
    private lateinit var faqViewModel: FaqViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        supportActionBar?.title = getString(R.string.title_faq_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvFaq.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFaq.adapter = faqAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        faqViewModel = ViewModelProvider(this@FaqActivity).get(FaqViewModel::class.java)

        faqViewModel.faq.observe(this, Observer { list ->
            pbActivity.visibility = View.GONE
            faqAdapter.items.clear()
            faqAdapter.items.addAll(list)
            faqAdapter.notifyDataSetChanged()
        })
    }
}
