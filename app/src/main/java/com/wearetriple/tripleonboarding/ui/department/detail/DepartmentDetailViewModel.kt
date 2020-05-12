package com.wearetriple.tripleonboarding.ui.department.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wearetriple.tripleonboarding.model.Department

class DepartmentDetailViewModel : ViewModel() {
    val department = MutableLiveData<Department>()

    /**
     * Keeps track of currently selected department for the detail view.
     */
    fun initDepartment(department: Department) {
        this.department.value = department
    }
}