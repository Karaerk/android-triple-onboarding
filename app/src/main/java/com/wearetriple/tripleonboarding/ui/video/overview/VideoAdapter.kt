package com.wearetriple.tripleonboarding.ui.video.overview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
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

            val roundingRadius = 5
            val thumbnailSize = 0.25f
            val reqOpt = fitCenterTransform()
                .transform(RoundedCorners(roundingRadius))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(
                    itemView.ivVideoThumbnail.width,
                    itemView.ivVideoThumbnail.height
                )

            Glide.with(itemView.context)
                .load(item.thumbnail)
                .thumbnail(thumbnailSize)
                .apply(reqOpt)
                .placeholder(ColorDrawable(Color.WHITE))
                .into(itemView.ivVideoThumbnail)
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