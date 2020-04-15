package com.wearetriple.tripleonboarding.ui.department.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.android.synthetic.main.activity_department_detail.*

class DepartmentDetailActivity : AppCompatActivity() {

    private lateinit var departmentDetailViewModel: DepartmentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        departmentDetailViewModel =
            ViewModelProvider(this@DepartmentDetailActivity).get(DepartmentDetailViewModel::class.java)

        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews(department: Department) {
        supportActionBar?.title = department.title
        Glide.with(this).load(department.image).into(ivDepartment)

        tvContent.text = HtmlCompat.fromHtml(department.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvContent.movementMethod = LinkMovementMethod.getInstance()
        Linkify.addLinks(tvContent, Linkify.WEB_URLS)
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        departmentDetailViewModel.initDepartment(intent.getParcelableExtra(DepartmentDetailViewModel.CLICKED_DEPARTMENT))

        departmentDetailViewModel.department.observeNonNull(this, this::initViews)
    }
}
