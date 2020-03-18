package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Fact
import kotlinx.android.synthetic.main.item_fact.view.*

/**
 * Used to prepare recyclerview's items.
 */
class FactAdapter(
    private val facts: List<Fact>,
    private val clickListener: (Fact) -> Unit
) : RecyclerView.Adapter<FactAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fact: Fact) {
            itemView.btnFact.text = fact.title
            itemView.btnFact.setOnClickListener { clickListener(fact) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        )
    }

    /**
     * Returns the size of the list.
     */
    override fun getItemCount(): Int {
        return facts.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(facts[position])
    }

}