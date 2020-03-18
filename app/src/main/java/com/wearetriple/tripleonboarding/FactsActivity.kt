package com.wearetriple.tripleonboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.FactAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.Fact
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_facts.*

class FactsActivity : AppCompatActivity() {

    private val facts = arrayListOf<Fact>()
    private val repository = BaseEntityRepository<Fact>(Fact.DATABASE_KEY)
    private val factAdapter =
        FactAdapter(facts) { fact: Fact -> factClicked(fact) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        supportActionBar?.title = getString(R.string.title_facts_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvFacts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFacts.adapter = factAdapter

        repository.getAll(object : DataCallback<Fact> {
            override fun onCallback(list: ArrayList<Fact>) {
                if (facts.isNotEmpty())
                    facts.clear()

                facts.addAll(list)

                factAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * Opens up a pop-up with details included about the clicked fact.
     */
    private fun factClicked(fact: Fact) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(fact.content)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.btn_close_popup)) { dialog, _ ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setTitle(fact.title)

        alert.show()
    }
}
