package com.wearetriple.tripleonboarding.ui.department.overview

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Department
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_department_overview.view.*


/**
 * Used to prepare recyclerview's items.
 */
class DepartmentOverviewAdapter(
    override val clickListener: (Department) -> Unit
) : AbstractAdapter<Department>(clickListener) {

    override var items: List<Department> = listOf()

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Department>.ViewHolder(itemView) {

        override fun bind(item: Department) {
            itemView.tvDepartmentTitle.text = item.title
            itemView.cvDepartment.setOnClickListener { clickListener(item) }

            val roundingRadius = 5
            val thumbnailSize = 0.25f
            val reqOpt = RequestOptions.fitCenterTransform()
                .transform(RoundedCorners(roundingRadius))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(
                    itemView.ivDepartmentThumbnail.width,
                    itemView.ivDepartmentThumbnail.height
                )

            Glide.with(itemView.context)
                .load(item.thumbnail)
                .thumbnail(thumbnailSize)
                .apply(reqOpt)
                .placeholder(R.drawable.triple_icon)
                .into(itemView.ivDepartmentThumbnail)

        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Department>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_department_overview,
                parent,
                false
            )
        )
    }

}