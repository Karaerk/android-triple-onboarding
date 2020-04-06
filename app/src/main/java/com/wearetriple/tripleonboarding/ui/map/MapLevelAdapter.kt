package com.wearetriple.tripleonboarding.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.MapLevel
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_map_level.view.*

/**
 * Used to prepare recyclerview's items.
 */
class MapLevelAdapter(
    override val items: List<MapLevel>,
    override val clickListener: (MapLevel) -> Unit
) : AbstractAdapter<MapLevel>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<MapLevel>.ViewHolder(itemView) {

        override fun bind(item: MapLevel) {
            itemView.btnMapLevel.text = item.level
            itemView.btnMapLevel.setOnClickListener { clickListener(item) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<MapLevel>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_map_level, parent, false)
        )
    }

}