package com.wearetriple.tripleonboarding

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.squareup.picasso.Picasso
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.android.synthetic.main.activity_department_detail.*

const val CLICKED_DEPARTMENT = "CLICKED_DEPARTMENT"

class DepartmentDetailActivity : AppCompatActivity() {

    private lateinit var department: Department

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_detail)

        department = intent.getParcelableExtra(CLICKED_DEPARTMENT)
        supportActionBar?.title = department.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        Picasso.get().load(department.image).into(ivDepartment)
        tvContent.text = HtmlCompat.fromHtml(department.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
