package com.wearetriple.tripleonboarding.ui.department.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wearetriple.tripleonboarding.R
import kotlinx.android.synthetic.main.activity_department_detail.*

class DepartmentDetailActivity : AppCompatActivity() {

    private lateinit var departmentDetailViewModel: DepartmentDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        departmentDetailViewModel =
            ViewModelProvider(this@DepartmentDetailActivity).get(DepartmentDetailViewModel::class.java)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        departmentDetailViewModel.department.observe(this, Observer {
            supportActionBar?.title = it.title
            Glide.with(this).load(it.image).into(ivDepartment)

            tvContent.text = HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvContent.movementMethod = LinkMovementMethod.getInstance();
            Linkify.addLinks(tvContent, Linkify.WEB_URLS)
        })


    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        departmentDetailViewModel.department.value =
            intent.getParcelableExtra(DepartmentDetailViewModel.CLICKED_DEPARTMENT)
    }
}
