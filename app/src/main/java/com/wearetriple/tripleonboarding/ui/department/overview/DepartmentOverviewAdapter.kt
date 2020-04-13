package com.wearetriple.tripleonboarding.ui.department.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Department
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_department_overview.view.*

/**
 * Used to prepare recyclerview's items.
 */
class DepartmentOverviewAdapter(
    override var items: ArrayList<Department>,
    override val clickListener: (Department) -> Unit
) : AbstractAdapter<Department>(items, clickListener) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Department>.ViewHolder(itemView) {

        override fun bind(item: Department) {
            itemView.tvDepartmentTitle.text = item.title
            itemView.cvDepartment.setOnClickListener { clickListener(item) }

            Glide.with(itemView.context).load(item.thumbnail).into(itemView.ivDepartmentThumbnail)
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