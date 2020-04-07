package com.wearetriple.tripleonboarding.ui.department.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wearetriple.tripleonboarding.model.Department

class DepartmentDetailViewModel : ViewModel() {
    val department = MutableLiveData<Department>()

    companion object {
        const val CLICKED_DEPARTMENT = "CLICKED_DEPARTMENT"
    }
}