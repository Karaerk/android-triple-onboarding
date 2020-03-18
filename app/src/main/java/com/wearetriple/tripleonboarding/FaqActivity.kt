package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.FaqAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.Faq
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_faq.*

class FaqActivity : AppCompatActivity() {

    private val faq = arrayListOf<Faq>()
    private val repository = BaseEntityRepository<Faq>(Faq.DATABASE_KEY)
    private val factAdapter = FaqAdapter(faq)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        supportActionBar?.title = getString(R.string.title_faq_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvFaq.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFaq.adapter = factAdapter

        repository.getAll(object : DataCallback<Faq> {
            override fun onCallback(list: ArrayList<Faq>) {
                if (faq.isNotEmpty())
                    faq.clear()

                faq.addAll(list)

                factAdapter.notifyDataSetChanged()
            }
        })
    }
}
