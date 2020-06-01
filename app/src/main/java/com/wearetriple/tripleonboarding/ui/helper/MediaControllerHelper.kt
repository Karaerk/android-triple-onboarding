package com.wearetriple.tripleonboarding.ui.helper

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import com.wearetriple.tripleonboarding.R

/**
 * Allows the app to only show a video in fullscreen.
 * It also directs the user back to the previous activity when clicking on exit fullscreen button.
 */
class MediaControllerHelper(context: Context?) :
    MediaController(context) {
    private val activity = context as Activity
    private lateinit var fullScreen: ImageButton

    override fun setAnchorView(view: View) {
        super.setAnchorView(view)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        val customContext = ContextThemeWrapper(activity, R.style.AppTheme_Widget_VideoView)
        fullScreen = ImageButton(customContext, null, 0)
        fullScreen.setImageResource(R.drawable.ic_fullscreen_exit_white_32dp)

        val uiSpaceVertical = 30
        val uiSpaceHorizontal = 5
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.END
        params.topMargin = uiSpaceVertical
        params.rightMargin = uiSpaceHorizontal
        addView(fullScreen, params)

        fullScreen.setOnClickListener {
            activity.finish()
        }
    }
}
