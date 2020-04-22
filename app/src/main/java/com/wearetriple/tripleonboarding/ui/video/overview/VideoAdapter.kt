package com.wearetriple.tripleonboarding.ui.video.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Video
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_video.view.*

/**
 * Used to prepare recyclerview's items.
 */
class VideoAdapter(
    override var items: ArrayList<Video>,
    override val clickListener: (Video) -> Unit
) : AbstractAdapter<Video>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Video>.ViewHolder(itemView) {

        override fun bind(item: Video) {
            itemView.tvTitle.text = item.title
            itemView.cvVideo.setOnClickListener { clickListener(item) }

            Glide.with(itemView.context).load(item.thumbnail).into(itemView.ivVideoThumbnail)
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Video>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        )
    }

}