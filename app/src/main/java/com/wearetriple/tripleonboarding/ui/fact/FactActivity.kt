package com.wearetriple.tripleonboarding.ui.fact

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.android.synthetic.main.activity_facts.*

class FactActivity : AppCompatActivity() {

    private val facts = arrayListOf<Fact>()
    private val factAdapter =
        FactAdapter(facts) { fact: Fact ->
            factClicked(
                fact
            )
        }
    private lateinit var factViewModel: FactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)
        supportActionBar?.title = getString(R.string.title_facts_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvFacts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFacts.adapter = factAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        factViewModel = ViewModelProvider(this@FactActivity).get(FactViewModel::class.java)

        val liveData = factViewModel.getAll()

        liveData.observe(this, Observer { list ->
            if (list != null) {
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
            .setNegativeButton(getString(R.string.btn_close_dialog)) { dialog, _ ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setTitle(fact.title)

        alert.show()
    }
}
