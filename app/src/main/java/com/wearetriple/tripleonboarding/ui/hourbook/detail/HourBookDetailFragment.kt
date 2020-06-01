package com.wearetriple.tripleonboarding.ui.hourbook.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

class HourBookDetailFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private lateinit var hourBookDetailViewModel: HourBookDetailViewModel
    private val args: HourBookDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_book_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        hourBookDetailViewModel =
            ViewModelProvider(activityContext).get(HourBookDetailViewModel::class.java)

        initViewModel()
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
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
            HourBookDetailAdapter(
                list
            ) { hourBookChild: HourBookChild ->
                hourBookChildClicked(hourBookChild)
            }
        rvChild.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        rvChild.adapter = hourBookChildAdapter
        hourBookChildAdapter.notifyDataSetChanged()
    }

    /**
     * Launches a browser to show the web page linked to the action's url.
     */
    private fun launchBrowser() {
        var url = hourBookDetailViewModel.hourBookTopic.value!!.action?.url!!
        url = hourBookDetailViewModel.getProperlyFormattedUrl(url)

        CustomTabsHelper.launchUrl(activityContext, url)
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun hourBookChildClicked(hourBookChild: HourBookChild) {
        val dialogBuilder = AlertDialog.Builder(activityContext)

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
