package com.wearetriple.tripleonboarding.ui.hourbook.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.HourBookChild
import com.wearetriple.tripleonboarding.model.helper.CustomTabsHelper
import kotlinx.android.synthetic.main.activity_hour_book_detail.*
import kotlinx.android.synthetic.main.content_hour_book_detail.*

class HourBookDetailActivity : AppCompatActivity() {

    private lateinit var hourBookDetailViewModel: HourBookDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_book_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hourBookDetailViewModel =
            ViewModelProvider(this@HourBookDetailActivity).get(HourBookDetailViewModel::class.java)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        hourBookDetailViewModel.hourBookTopic.observe(this, Observer {
            tvContent.text =
                HtmlCompat.fromHtml(it.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvContent.movementMethod = LinkMovementMethod.getInstance()
            Linkify.addLinks(tvContent, Linkify.WEB_URLS)

            if (hourBookDetailViewModel.isActionPresent()) {
                fabLaunchUrl.visibility = VISIBLE
                fabLaunchUrl.setOnClickListener { launchBrowser() }
            }

            if (hourBookDetailViewModel.isChildSubjectsPresent()) {
                llChildInfo.setPadding(0)
                rvChild.visibility = VISIBLE
                rvChild.isFocusable = false
                val hourBookChildAdapter =
                    HourBookDetailAdapter(
                        it.child
                    ) { hourBookChild: HourBookChild ->
                        hourBookChildClicked(hourBookChild)
                    }
                rvChild.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rvChild.adapter = hourBookChildAdapter
                hourBookChildAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        hourBookDetailViewModel.hourBookTopic.value =
            intent.getParcelableExtra(HourBookDetailViewModel.CLICKED_HOUR_BOOK_TOPIC)
    }

    /**
     * Launches a browser to show the web page linked to the action's url.
     */
    private fun launchBrowser() {
        var url = hourBookDetailViewModel.hourBookTopic.value!!.action?.url!!
        url = hourBookDetailViewModel.getProperlyFormattedUrl(url)

        CustomTabsHelper.launchUrl(this, url)
    }

    /**
     * Opens up a pop-up with details included about the clicked topic.
     */
    private fun hourBookChildClicked(hourBookChild: HourBookChild) {
        val dialogBuilder = AlertDialog.Builder(this)

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
