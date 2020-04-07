package com.wearetriple.tripleonboarding.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.InfoTopic
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_info_topic.view.*

/**
 * Used to prepare recyclerview's items.
 */
class InfoTopicAdapter(
    override val items: List<InfoTopic>,
    override val clickListener: (InfoTopic) -> Unit
) : AbstractAdapter<InfoTopic>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<InfoTopic>.ViewHolder(itemView) {

        override fun bind(item: InfoTopic) {
            itemView.btnInfoTopic.text = item.title
            itemView.btnInfoTopic.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<InfoTopic>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_info_topic, parent, false)
        )
    }

}