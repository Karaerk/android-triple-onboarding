package com.wearetriple.tripleonboarding.ui.signin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException
import com.wearetriple.tripleonboarding.R
import org.json.JSONObject


class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private lateinit var singleAccountApp: ISingleAccountPublicClientApplication
    val account = MutableLiveData<IAccount>()
    val justSignedOut = MutableLiveData<Boolean>()
    val signInOnStartUp = MutableLiveData<Boolean>()
    val user = account

    companion object {
        private var TAG = SignInFragment::class.java.simpleName
    }

    /**
     * Creates a PublicClientApplication object with res/raw/auth_config_single_account.json.
     */
    fun createPublicClientApplication() {
        PublicClientApplication.createSingleAccountPublicClientApplication(
            context as Context,
            R.raw.auth_config_single_account,
            object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                override fun onCreated(application: ISingleAccountPublicClientApplication) {
                    singleAccountApp = application

                    loadAccount()
                }

                override fun onError(exception: MsalException) {
                    Log.e(TAG, exception.message)
                }
            })
    }

    /**
     * Load the currently signed-in account, if there's any.
     * If the account is removed the device, the app can also perform the clean-up work in onAccountChanged().
     */
    fun loadAccount() {
        if (!this::singleAccountApp.isInitialized)
            return

        singleAccountApp.getCurrentAccountAsync(object :
            ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                account.value = activeAccount

                if (activeAccount == null)
                    signInOnStartUp.value = false
            }

            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    account.value = null
                    justSignedOut.value = true
                }
            }

            override fun onError(exception: MsalException) {
                Log.e(TAG, exception.toString())
            }
        })
    }

    fun attemptSignIn(activity: Activity) {
        if (!this::singleAccountApp.isInitialized)
            return

        singleAccountApp.signIn(
            activity,
            "",
            arrayOf("user.read"),
            getAuthInteractiveCallback()
        )
    }


    /**
     * Callback used for interactive request.
     * If succeeds we use the access token to call the Microsoft Graph.
     * Does not check cache.
     */
    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {

            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated")
                Log.d(TAG, "ID Token: " + authenticationResult.account.claims!!["id_token"])

                account.value = authenticationResult.account
                callGraphAPI(authenticationResult)
            }

            override fun onError(exception: MsalException) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: $exception")
            }

            override fun onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.")
            }
        }
    }

    /**
     * Make an HTTP request to obtain MSGraph data
     */
    private fun callGraphAPI(authenticationResult: IAuthenticationResult) {
        val graphResourceUrl = "https://graph.microsoft.com/v1.0/me"
        MSGraphRequestWrapper.callGraphAPIWithVolley(
            context as Context,
            graphResourceUrl,
            authenticationResult.accessToken,
            Response.Listener<JSONObject> { response ->
                /* Successfully called graph, process data and send to UI */
                Log.d(TAG, "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.d(TAG, "Error: $error")
            })
    }
}