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
        private val roundingRadius = 5
        private val thumbnailSize = 0.25f

        private val requestOptions = fitCenterTransform()
            .transform(RoundedCorners(roundingRadius))
            .error(ColorDrawable(Color.WHITE))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(
                itemView.ivVideoThumbnail.width,
                itemView.ivVideoThumbnail.height
            )

        override fun bind(item: Video) {
            itemView.tvTitle.text = item.title
            itemView.cvVideo.setOnClickListener { clickListener(item) }

            prepareThumbnail(item)
        }

        /**
         * Prepares the video's thumbnail by generating a thumbnail out of the video whenever
         * there's no custom thumbnail available in [item]
         */
        private fun prepareThumbnail(item: Video) {

            if (item.thumbnail.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(item.thumbnail)
                    .thumbnail(thumbnailSize)
                    .apply(requestOptions)
                    .placeholder(ColorDrawable(Color.WHITE))
                    .into(itemView.ivVideoThumbnail)
            } else {
                generateThumbnail(item)
            }
        }

        /**
         * Picks up a frame of the item's linked video and uses that as a thumbnail in case the
         * item's thumbnail attribute is empty.
         */
        private fun generateThumbnail(item: Video) {
            Glide.with(itemView.context)
                .asBitmap()
                .load(item.url)
                .thumbnail(thumbnailSize)
                .apply(requestOptions)
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