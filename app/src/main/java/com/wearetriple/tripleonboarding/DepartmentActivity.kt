package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.DepartmentAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.Department
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_department.*

class DepartmentActivity : AppCompatActivity() {

    private val departments = arrayListOf<Department>()
    private val repository = BaseEntityRepository<Department>(Department.DATABASE_KEY)
    private val departmentAdapter = DepartmentAdapter(departments) { department -> println(department) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)
        supportActionBar?.title = getString(R.string.title_department_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvDepartments.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvDepartments.adapter = departmentAdapter

        repository.getAll(object : DataCallback<Department> {
            override fun onCallback(list: ArrayList<Department>) {
                if (departments.isNotEmpty())
                    departments.clear()

                departments.addAll(list)

                departmentAdapter.notifyDataSetChanged()
            }
        })

    }
}
