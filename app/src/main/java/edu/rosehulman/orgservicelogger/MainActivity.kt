package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.login.OnLoginButtonPressedListener
import edu.rosehulman.orgservicelogger.login.SplashFragment

private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity(), OnLoginButtonPressedListener {
    private lateinit var userId: String
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeAuthStateListener()
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
    }

    private fun makeAuthStateListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            Log.d(Constants.TAG, "In the auth listener, user is $user")

            val fragment = if (user != null) {
                userId = user.uid
                HomeFragment()
            } else {
                SplashFragment(this)
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.activity_main_frame, fragment)
            transaction.commit()
        }
    }

    override fun onLoginButtonPressed() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val loginIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        startActivityForResult(loginIntent, RC_SIGN_IN)
    }
}
