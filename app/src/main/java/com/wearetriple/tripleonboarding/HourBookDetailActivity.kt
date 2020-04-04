package com.wearetriple.tripleonboarding

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.HourBookChildAdapter
import com.wearetriple.tripleonboarding.model.HourBookChild
import com.wearetriple.tripleonboarding.model.HourBookTopic
import com.wearetriple.tripleonboarding.model.helper.CustomTabsHelper
import kotlinx.android.synthetic.main.activity_hour_book_detail.*
import kotlinx.android.synthetic.main.content_hour_book_detail.*

const val CLICKED_HOUR_BOOK_TOPIC = "CLICKED_HOUR_BOOK_TOPIC"

class HourBookDetailActivity : AppCompatActivity() {

    private lateinit var hourBookTopic: HourBookTopic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_book_detail)
        setSupportActionBar(toolbar)

        hourBookTopic = intent.getParcelableExtra(CLICKED_HOUR_BOOK_TOPIC)
        supportActionBar?.title = hourBookTopic.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        tvContent.text =
            HtmlCompat.fromHtml(hourBookTopic.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tvContent.movementMethod = LinkMovementMethod.getInstance();
        Linkify.addLinks(tvContent, Linkify.WEB_URLS)

        if (isActionPresent()) {
            fabLaunchUrl.visibility = VISIBLE
            fabLaunchUrl.setOnClickListener { launchBrowser() }
        }

        if (isChildSubjectsPresent()) {
            llChildInfo.setPadding(0)
            rvChild.visibility = VISIBLE
            rvChild.isFocusable = false
            val hourBookChildAdapter =
                HourBookChildAdapter(hourBookTopic.child) { hourBookChild: HourBookChild ->
                    hourBookChildClicked(hourBookChild)
                }
            rvChild.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvChild.adapter = hourBookChildAdapter
            hourBookChildAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Launches a browser to show the web page linked to the action's url.
     */
    private fun launchBrowser() {
        var url = hourBookTopic.action?.url!!

        if (!isUrlProperlyFormatted(url))
            url = "http://$url"

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

    private fun isActionPresent() = hourBookTopic.action != null
    private fun isChildSubjectsPresent() = hourBookTopic.child.size > 0
    private fun isUrlProperlyFormatted(url: String) =
        (url.startsWith("http://") || url.startsWith("https://"))
}
