package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.InfoTopic
import kotlinx.android.synthetic.main.item_info_topic.view.*

/**
 * Used to prepare recyclerview's items.
 */
class InfoTopicAdapter(
    private val infoTopics: List<InfoTopic>,
    private val clickListener: (InfoTopic) -> Unit
) : RecyclerView.Adapter<InfoTopicAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(infoTopic: InfoTopic) {
            itemView.btnInfoTopic.text = infoTopic.title
            itemView.btnInfoTopic.setOnClickListener { clickListener(infoTopic) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_info_topic, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return infoTopics.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(infoTopics[position])
    }

}