package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import edu.rosehulman.orgservicelogger.login.LoggedInSplashFragment
import edu.rosehulman.orgservicelogger.login.OnLoginButtonPressedListener
import edu.rosehulman.orgservicelogger.login.SplashFragment
import edu.rosehulman.orgservicelogger.notifications.NotificationLauncher
import edu.rosehulman.orgservicelogger.organization.ChooseOrganizationFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_confirm_information.view.*

class MainActivity : AppCompatActivity(), OnLoginButtonPressedListener {
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)
        NotificationLauncher.createNotificationChannels(this)
        makeAuthStateListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
            val lastUserId = userId
            if (lastUserId != null) {
                NotificationLauncher.descheduleNotifications(this, lastUserId)
            }
            val user = auth.currentUser
            if (user != null) {
                userId = user.uid
                Log.d(Constants.TAG, "User logged in with id: $userId")

                retrievePersonExists(user.uid) { person ->
                    if (person != null) {
                        Log.d(Constants.TAG, "Logging in as $userId")
                        switchMainFragment(this, LoggedInSplashFragment(person))
                    } else {
                        Log.d(Constants.TAG, "Registering $userId")
                        val view =
                            layoutInflater.inflate(R.layout.dialog_confirm_information, null, false)
                        if (user.displayName != null) {
                            view.dialog_confirm_information_name.setText(user.displayName.toString())
                        }
                        if (user.email != null) {
                            view.dialog_confirm_information_email.setText(user.email.toString())
                        }
                        if (user.phoneNumber != null) {
                            view.dialog_confirm_information_phone.setText(user.phoneNumber.toString())
                        }

                        val builder = AlertDialog.Builder(this)
                        builder.setView(view)
                        builder.setTitle(getString(R.string.text_dialog_confirm_user_info))
                        builder.setPositiveButton(android.R.string.ok) { _, _ ->
                            val person = Person()
                            person.name = view.dialog_confirm_information_name.text.toString()
                            person.email = view.dialog_confirm_information_email.text.toString()
                            person.phone = view.dialog_confirm_information_phone.text.toString()
                            person.canDrive = view.dialog_confirm_information_canDrive.isChecked
                            person.id = user.uid
                            writePerson(person)

                            switchMainFragment(this, LoggedInSplashFragment(person))
                        }
                        builder.setNegativeButton(android.R.string.cancel, null)
                        builder.create().show()
                    }
                }
            } else {
                Log.d(Constants.TAG, "Login failed")
                switchMainFragment(this, SplashFragment(this))
            }
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
        startActivity(loginIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
