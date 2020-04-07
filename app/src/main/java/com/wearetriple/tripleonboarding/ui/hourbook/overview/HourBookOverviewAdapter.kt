package com.wearetriple.tripleonboarding.ui.hourbook.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_hour_book_topic.view.*

/**
 * Used to prepare recyclerview's items.
 */
class HourBookOverviewAdapter(
    override val items: List<HourBookTopic>,
    override val clickListener: (HourBookTopic) -> Unit
) : AbstractAdapter<HourBookTopic>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<HourBookTopic>.ViewHolder(itemView) {

        override fun bind(item: HourBookTopic) {
            itemView.btnHourBookTopic.text = item.title
            itemView.btnHourBookTopic.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<HourBookTopic>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour_book_topic,
                parent,
                false
            )
        )
    }

}