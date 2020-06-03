package com.wearetriple.tripleonboarding.ui.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_more.view.*

/**
 * Used to prepare recyclerview's items.
 */
class MoreAdapter(
    override val clickListener: (String) -> Unit
) : AbstractAdapter<String>(clickListener) {

    override var items: List<String> = listOf()

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<String>.ViewHolder(itemView) {

        override fun bind(item: String) {
            itemView.tvTitle.text = item
            itemView.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<String>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_more, parent, false)
        )
    }

}