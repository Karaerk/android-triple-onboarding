package com.wearetriple.tripleonboarding.ui.department.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.android.synthetic.main.fragment_department_overview.*

class DepartmentOverviewFragment : Fragment(R.layout.fragment_department_overview) {

    private lateinit var activityContext: AppCompatActivity
    private val departmentAdapter =
        DepartmentOverviewAdapter { department -> onDepartmentClick(department) }
    private lateinit var departmentOverviewViewModel: DepartmentOverviewViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        rvDepartments.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvDepartments.adapter = departmentAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        departmentOverviewViewModel =
            ViewModelProvider(activityContext).get(DepartmentOverviewViewModel::class.java)

        departmentOverviewViewModel.departments.observeNonNull(
            viewLifecycleOwner,
            this::initRecyclerView
        )
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Department>) {
        pbActivity.visibility = View.GONE
        departmentAdapter.items = list
        departmentAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a detail fragment containing data of clicked department.
     */
    private fun onDepartmentClick(department: Department) {
        val action =
            DepartmentOverviewFragmentDirections.actionDepartmentOverviewFragmentToDepartmentDetailFragment(
                department,
                department.title
            )
        findNavController().navigate(action)
    }
}
