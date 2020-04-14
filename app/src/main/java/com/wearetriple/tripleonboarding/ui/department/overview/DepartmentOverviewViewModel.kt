package com.wearetriple.tripleonboarding.ui.department.overview

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.wearetriple.tripleonboarding.database.FirebaseQueryLiveData
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentOverviewViewModel : ViewModel() {
    private val liveData = FirebaseQueryLiveData(DATABASE_REF)
    private val departmentLiveData = MediatorLiveData<List<Department>>()
    val departments = departmentLiveData

    companion object {
        private const val DATABASE_KEY = "department"
        private val DATABASE_REF = FirebaseDatabase.getInstance().getReference(DATABASE_KEY)
    }

    init {
        departmentLiveData.addSource(liveData) { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModelScope.launch {
                    postLiveData(dataSnapshot)
                }
            } else {
                departmentLiveData.setValue(arrayListOf())
            }
        }
    }

    private suspend fun postLiveData(dataSnapshot: DataSnapshot) = withContext(Dispatchers.IO) {
        val list = ArrayList<Department>()

        dataSnapshot.children.forEach {
            val item: Department? = it.getValue(Department::class.java)

            if (item != null)
                list.add(item)
        }

        departmentLiveData.postValue(list)
    }
}