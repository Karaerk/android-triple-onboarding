package com.wearetriple.tripleonboarding.ui.fact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.android.synthetic.main.fragment_facts.*

class FactFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val factAdapter =
        FactAdapter(arrayListOf()) { fact: Fact ->
            factClicked(
                fact
            )
        }
    private lateinit var factViewModel: FactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facts, container, false)
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
        rvFacts.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvFacts.adapter = factAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        factViewModel = ViewModelProvider(activityContext).get(FactViewModel::class.java)

        factViewModel.facts.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Fact>) {
        pbActivity.visibility = View.GONE
        factAdapter.items.clear()
        factAdapter.items.addAll(list)
        factAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a pop-up with details included about the clicked fact.
     */
    private fun factClicked(fact: Fact) {
        val dialogBuilder = AlertDialog.Builder(activityContext)

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
