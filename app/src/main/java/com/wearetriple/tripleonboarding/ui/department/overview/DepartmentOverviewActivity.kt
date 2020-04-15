package com.wearetriple.tripleonboarding.ui.department.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Department
import com.wearetriple.tripleonboarding.ui.department.detail.DepartmentDetailActivity
import com.wearetriple.tripleonboarding.ui.department.detail.DepartmentDetailViewModel
import kotlinx.android.synthetic.main.activity_department_overview.*

class DepartmentOverviewActivity : AppCompatActivity() {

    private val departmentAdapter =
        DepartmentOverviewAdapter(
            arrayListOf()
        ) { department -> onDepartmentClick(department) }
    private lateinit var departmentOverviewViewModel: DepartmentOverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_overview)
        supportActionBar?.title = getString(R.string.title_department_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvDepartments.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvDepartments.adapter = departmentAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        departmentOverviewViewModel =
            ViewModelProvider(this@DepartmentOverviewActivity).get(DepartmentOverviewViewModel::class.java)
        
        departmentOverviewViewModel.departments.observeNonNull(this, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<Department>) {
        pbActivity.visibility = View.GONE
        departmentAdapter.items.clear()
        departmentAdapter.items.addAll(list)
        departmentAdapter.notifyDataSetChanged()
    }

    /**
     * Opens up a detail activity containing data of clicked department.
     */
    private fun onDepartmentClick(department: Department) {
        val intent = Intent(this@DepartmentOverviewActivity, DepartmentDetailActivity::class.java)

        intent.putExtra(DepartmentDetailViewModel.CLICKED_DEPARTMENT, department)
        startActivity(intent)
    }
}
