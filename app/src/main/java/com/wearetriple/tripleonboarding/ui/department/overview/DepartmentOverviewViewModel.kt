package com.wearetriple.tripleonboarding.ui.department.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.coroutines.launch

class DepartmentOverviewViewModel : ViewModel() {
    private val repository = EntityRepository()
    private val departmentLiveData = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> = departmentLiveData

    companion object {
        private const val DATABASE_KEY = "department"
    }

    init {
        viewModelScope.launch {
            postLiveData()
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData() {
        val data = repository.getAllFromTable<Department>(DATABASE_KEY)
        departmentLiveData.postValue(data)
    }
}