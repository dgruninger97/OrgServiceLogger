package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import edu.rosehulman.orgservicelogger.login.OnLoginButtonPressedListener
import edu.rosehulman.orgservicelogger.login.SplashFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_confirm_information.view.*
import java.util.zip.Inflater

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
                var person = Person()
                var builder = AlertDialog.Builder(this)
                var view = layoutInflater.inflate(R.layout.dialog_confirm_information, null, false)
                view.dialog_confirm_information_name.setText(user.displayName.toString())
                view.dialog_confirm_information_email.setText(user.email.toString())
                view.dialog_confirm_information_phone.setText(user.phoneNumber.toString())
                builder.setView(view)
                builder.setTitle("Please confirm the info")
                builder.setPositiveButton(android.R.string.ok) { _, _ ->
                    person.name = view.dialog_confirm_information_name.text.toString()
                    person.email = view.dialog_confirm_information_email.text.toString()
                    person.phone = view.dialog_confirm_information_phone.text.toString()
                    person.canDrive = view.dialog_confirm_information_canDrive.isChecked
                    person.id = user.uid
                    switchMainFragment(this, HomeFragment(person))
                }
                builder.setNegativeButton(android.R.string.cancel, null)
                builder.create().show()
                userId = user.uid
                Log.d(Constants.TAG, "In the auth listener, user id is ${user.uid}")

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
                auth?.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
