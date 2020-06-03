package com.wearetriple.tripleonboarding.ui.department.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Department
import kotlinx.android.synthetic.main.fragment_department_detail.*

class DepartmentDetailFragment : Fragment(R.layout.fragment_department_detail) {

    private lateinit var departmentDetailViewModel: DepartmentDetailViewModel
    private val args: DepartmentDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews(department: Department) {
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
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        departmentDetailViewModel =
            ViewModelProvider(this).get(DepartmentDetailViewModel::class.java)
        departmentDetailViewModel.initDepartment(args.department)

        departmentDetailViewModel.department.observeNonNull(viewLifecycleOwner, this::initViews)
    }
}
