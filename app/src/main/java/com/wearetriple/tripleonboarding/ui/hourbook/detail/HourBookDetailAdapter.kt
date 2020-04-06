package com.wearetriple.tripleonboarding.ui.hourbook.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.HourBookChild
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_hour_book_child.view.*

/**
 * Used to prepare recyclerview's items.
 */
class HourBookDetailAdapter(
    override val items: List<HourBookChild>,
    override val clickListener: (HourBookChild) -> Unit
) : AbstractAdapter<HourBookChild>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<HourBookChild>.ViewHolder(itemView) {

        override fun bind(item: HourBookChild) {
            itemView.btnHourBookChild.text = item.code
            itemView.btnHourBookChild.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<HourBookChild>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour_book_child,
                parent,
                false
            )
        )
    }
}