package com.wearetriple.tripleonboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.android.synthetic.main.item_department.view.*

/**
 * Used to prepare recyclerview's items.
 */
class DepartmentAdapter(
    private val departments: List<Department>,
    private val clickListener: (Department) -> Unit
) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(department: Department) {
            itemView.tvDepartmentTitle.text = department.title
            itemView.btnDepartmentMoreInfo.setOnClickListener { clickListener(department) }
            itemView.cvDepartment.setOnClickListener { clickListener(department) }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_department, parent, false)
        )
    }

    /**
     * Returns the size of the list.
     */
    override fun getItemCount(): Int {
        return departments.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(departments[position])
    }

}