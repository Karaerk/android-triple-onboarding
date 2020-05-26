package com.wearetriple.tripleonboarding.ui.signin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.microsoft.identity.client.IAccount
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        initViewModel()

        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        signInViewModel.createPublicClientApplication()
    }

    override fun onResume() {
        super.onResume()

        initializeUI()
        /**
         * The account may have been removed from the device (if broker is in use).
         * Therefore, we want to update the account state by invoking loadAccount() here.
         */
        signInViewModel.loadAccount()
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        signInViewModel = ViewModelProvider(activityContext).get(SignInViewModel::class.java)

        signInViewModel.user.observeNonNull(viewLifecycleOwner, this::initAccount)
        signInViewModel.signInOnStartUp.observeNonNull(viewLifecycleOwner, this::signInOnStartUp)
        signInViewModel.justSignedOut.observeNonNull(viewLifecycleOwner, this::initSignOut)

    }

    /**
     * Makes sure the app prepares its state when user is not signed in.
     */
    private fun signInOnStartUp(signedIn: Boolean) {
        if (!signedIn)
            initAccount(null)
    }

    /**
     * Prepares the UI based on the user account's state.
     */
    private fun initAccount(account: IAccount?) {
        if (account != null) {
            findNavController().navigate(R.id.action_signInFragment_to_memoryFragment)
        } else {
            pbActivity.visibility = View.GONE
            clSignIn.visibility = View.VISIBLE
        }
    }

    /**
     * Gives the user feedback when signing out.
     */
    private fun initSignOut(signedOut: Boolean) {
        if (signedOut) {
            Toast.makeText(context, "Signed Out.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    /**
     * Initializes UI variables and callbacks.
     */
    private fun initializeUI() {
        btnSignIn.setOnClickListener {
            signInViewModel.attemptSignIn(activity as Activity)
        }
    }
}