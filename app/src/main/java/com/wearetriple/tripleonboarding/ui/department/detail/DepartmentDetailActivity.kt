package com.wearetriple.tripleonboarding.ui.department.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

        val roundingRadius = 5
        val thumbnailSize = 0.25f
        val reqOpt = RequestOptions.fitCenterTransform()
            .transform(RoundedCorners(roundingRadius))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(
                ivDepartment.width,
                ivDepartment.height
            )

        Glide.with(this)
            .load(department.image)
            .thumbnail(thumbnailSize)
            .apply(reqOpt)
            .placeholder(ColorDrawable(Color.WHITE))
            .into(ivDepartment)

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
