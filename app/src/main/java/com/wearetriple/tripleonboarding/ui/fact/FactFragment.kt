package com.wearetriple.tripleonboarding.ui.fact

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.android.synthetic.main.fragment_facts.*

class FactFragment : Fragment(R.layout.fragment_facts) {

    private val factAdapter =
        FactAdapter { fact: Fact ->
            factClicked(
                fact
            )
        }
    private lateinit var factViewModel: FactViewModel

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
        rvFacts.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvFacts.adapter = factAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        factViewModel = ViewModelProvider(requireActivity()).get(FactViewModel::class.java)

        factViewModel.facts.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Fact>) {
        pbActivity.visibility = View.GONE
        factAdapter.items = list
        factAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a pop-up with details included about the clicked fact.
     */
    private fun factClicked(fact: Fact) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

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
