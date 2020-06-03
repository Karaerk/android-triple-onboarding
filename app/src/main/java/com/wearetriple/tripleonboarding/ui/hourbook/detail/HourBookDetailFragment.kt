package com.wearetriple.tripleonboarding.ui.hourbook.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.HourBookChild
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.ui.helper.CustomTabsHelper
import kotlinx.android.synthetic.main.content_hour_book_detail.*
import kotlinx.android.synthetic.main.fragment_hour_book_detail.*

class HourBookDetailFragment : Fragment(R.layout.fragment_hour_book_detail) {

    private lateinit var hourBookDetailViewModel: HourBookDetailViewModel
    private val args: HourBookDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        hourBookDetailViewModel =
            ViewModelProvider(requireActivity()).get(HourBookDetailViewModel::class.java)

        hourBookDetailViewModel.initTopic(args.hourBookTopic)

        hourBookDetailViewModel.hourBookTopic.observeNonNull(viewLifecycleOwner, this::initDetails)
        hourBookDetailViewModel.actionPresent.observeNonNull(viewLifecycleOwner, this::initAction)
        hourBookDetailViewModel.childSubjects.observeNonNull(
            viewLifecycleOwner,
            this::initChildSubjects
        )
    }

    /**
     * Initializes the details of the topic.
     */
    private fun initDetails(hourBookTopic: HourBookTopic) {
        tvContent.text =
            HtmlCompat.fromHtml(hourBookTopic.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvContent.movementMethod = LinkMovementMethod.getInstance()
        Linkify.addLinks(tvContent, Linkify.WEB_URLS)
    }

    /**
     * Initializes the action connected to the topic.
     */
    private fun initAction(isPresent: Boolean) {
        if (isPresent) {
            fabLaunchUrl.visibility = VISIBLE
            fabLaunchUrl.setOnClickListener { launchBrowser() }
        }
    }

    /**
     * Initializes the child subjects connected to the topic.
     */
    private fun initChildSubjects(list: List<HourBookChild>) {
        llChildInfo.setPadding(0)
        rvChild.visibility = VISIBLE
        rvChild.isFocusable = false
        val hourBookChildAdapter =
            HourBookDetailAdapter { hourBookChild: HourBookChild ->
                hourBookChildClicked(hourBookChild)
            }
        hourBookChildAdapter.items = list
        rvChild.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        rvChild.adapter = hourBookChildAdapter
        hourBookChildAdapter.notifyDataSetChanged()
    }

    /**
     * Launches a browser to show the web page linked to the action's url.
     */
    private fun launchBrowser() {
        var url = hourBookDetailViewModel.hourBookTopic.value!!.action?.url!!
        url = hourBookDetailViewModel.getProperlyFormattedUrl(url)

        CustomTabsHelper.launchUrl(requireActivity(), url)
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun hourBookChildClicked(hourBookChild: HourBookChild) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        dialogBuilder.setMessage(
            HtmlCompat.fromHtml(
                hourBookChild.content,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        )
            .setCancelable(false)
            .setNegativeButton(getString(R.string.btn_close_dialog)) { dialog, _ ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setTitle(hourBookChild.code)

        alert.show()
    }
}
