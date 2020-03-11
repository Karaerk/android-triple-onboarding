package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.android.synthetic.main.item_map_level.view.*

class MapLevelAdapter(
    private val mapLevels: List<MapLevel>,
    private val clickListener: (MapLevel) -> Unit
) : RecyclerView.Adapter<MapLevelAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mapLevel: MapLevel) {
            itemView.btnMapLevel.text = mapLevel.level
            itemView.btnMapLevel.setOnClickListener { clickListener(mapLevel) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_map_level, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return mapLevels.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mapLevels[position])
    }

}