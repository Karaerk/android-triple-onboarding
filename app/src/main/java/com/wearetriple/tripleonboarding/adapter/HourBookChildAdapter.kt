package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.HourBookChild
import kotlinx.android.synthetic.main.item_hour_book_child.view.*

/**
 * Used to prepare recyclerview's items.
 */
class HourBookChildAdapter(
    private val childs: List<HourBookChild>,
    private val clickListener: (HourBookChild) -> Unit
) : RecyclerView.Adapter<HourBookChildAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hourBookChild: HourBookChild) {
            itemView.btnHourBookChild.text = hourBookChild.code
            itemView.btnHourBookChild.setOnClickListener { clickListener(hourBookChild) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour_book_child,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return childs.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(childs[position])
    }
}