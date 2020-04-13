package com.wearetriple.tripleonboarding.ui.fact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Fact
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_fact.view.*

/**
 * Used to prepare recyclerview's items.
 */
class FactAdapter(
    override var items: ArrayList<Fact>,
    override val clickListener: (Fact) -> Unit
) : AbstractAdapter<Fact>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Fact>.ViewHolder(itemView) {

        override fun bind(item: Fact) {
            itemView.btnFact.text = item.title
            itemView.btnFact.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Fact>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false)
        )
    }

}