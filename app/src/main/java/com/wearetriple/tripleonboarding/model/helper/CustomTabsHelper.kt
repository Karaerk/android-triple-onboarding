package com.wearetriple.tripleonboarding.model.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import com.wearetriple.tripleonboarding.R

/**
 * Aims to open up urls through a custom tab, unless there's no browser installed
 * with custom tabs included. If that's the case, it'll open up urls with the user's default browser.
 */
class CustomTabsHelper {

    companion object {
        /**
         * Launches a url either through a custom tab or through the user's default browser.
         */
        fun launchUrl(context: Context, url: String) {
            if (isCustomTabsAvailable(context)) {
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.primary_darkblue))
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, Uri.parse(url))
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.putExtra(
                    Intent.EXTRA_REFERRER,
                    Uri.parse("android-app://${context.packageName}")
                )
                context.startActivity(intent)
            }
        }

        /**
         * Checks if there's any browser with custom tabs available.
         */
        private fun isCustomTabsAvailable(context: Context): Boolean {
            // Get default VIEW intent handler that can view a web url.
            val activityIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))

            // Get all apps that can handle VIEW intents.
            val pm = context.packageManager
            val resolvedActivityList =
                pm.queryIntentActivities(activityIntent, 0)

            for (info in resolvedActivityList) {
                val serviceIntent = Intent()
                serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
                serviceIntent.setPackage(info.activityInfo.packageName)

                if (pm.resolveService(serviceIntent, 0) != null)
                    return true
            }
            return false
        }
    }
}