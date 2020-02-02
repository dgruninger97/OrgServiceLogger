package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.login.OnLoginButtonPressedListener
import edu.rosehulman.orgservicelogger.login.SplashFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity(), OnLoginButtonPressedListener {
    private lateinit var userId: String
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var auth:FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)
        makeAuthStateListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
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
            this.auth = auth
            Log.d(Constants.TAG, "In the auth listener, user is $user")
            val fragment = if (user != null) {
                userId = user.uid
                Log.d(Constants.TAG, "In the auth listener, user id is ${user.uid}")
                HomeFragment(userId)
            } else {
                Log.d(Constants.TAG, "Login failed")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                auth?.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
