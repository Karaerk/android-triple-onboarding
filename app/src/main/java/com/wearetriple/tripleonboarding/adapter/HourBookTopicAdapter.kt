package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.HourBookTopic
import kotlinx.android.synthetic.main.item_hour_book_topic.view.*

/**
 * Used to prepare recyclerview's items.
 */
class HourBookTopicAdapter(
    private val hourBookTopics: List<HourBookTopic>,
    private val clickListener: (HourBookTopic) -> Unit
) : RecyclerView.Adapter<HourBookTopicAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hourBookTopic: HourBookTopic) {
            itemView.btnHourBookTopic.text = hourBookTopic.title
            itemView.btnHourBookTopic.setOnClickListener { clickListener(hourBookTopic) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour_book_topic,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return hourBookTopics.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hourBookTopics[position])
    }

}