package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import edu.rosehulman.orgservicelogger.login.OnLoginButtonPressedListener
import edu.rosehulman.orgservicelogger.login.SplashFragment
import edu.rosehulman.orgservicelogger.organization.ChooseOrganizationFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_confirm_information.view.*

class MainActivity : AppCompatActivity(), OnLoginButtonPressedListener {
    private lateinit var userId: String
    private var realOrganization: Organization? = null
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private var auth: FirebaseAuth? = null
    private var orgRef = FirebaseFirestore
        .getInstance()
        .collection("organization")

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
            if (user != null) {
                isLoggingIn(user.uid) { loggedIn ->
                    if (loggedIn) {
                        retrievePerson(user.uid) { person: Person ->
                            retrieveOrganizationForPerson(person) { organization ->
                                if (organization != null) {
                                    switchMainFragment(this, HomeFragment(person, organization))
                                } else {
                                    switchMainFragment(this, ChooseOrganizationFragment(person))
                                }
                            }
                        }
                    } else {
                        var builder = AlertDialog.Builder(this)
                        var view =
                            layoutInflater.inflate(R.layout.dialog_confirm_information, null, false)
                        if(user.displayName != null){
                            view.dialog_confirm_information_name.setText(user.displayName.toString())
                        }
                        if(user.email != null){
                            view.dialog_confirm_information_email.setText(user.email.toString())
                        }
                        if(user.phoneNumber != null){
                            view.dialog_confirm_information_phone.setText(user.phoneNumber.toString())
                        }
                        builder.setView(view)
                        builder.setTitle("Please confirm the info")
                        builder.setPositiveButton(android.R.string.ok) { _, _ ->
                            val person = Person()
                            person.name = view.dialog_confirm_information_name.text.toString()
                            person.email = view.dialog_confirm_information_email.text.toString()
                            person.phone = view.dialog_confirm_information_phone.text.toString()
                            person.canDrive = view.dialog_confirm_information_canDrive.isChecked
                            person.id = user.uid
                            writePerson(person)
                            retrieveOrganizationForPerson(person) { organization ->
                                if (organization != null) {
                                    // this should never happen but it might when we add invites
                                    switchMainFragment(this, HomeFragment(person, organization))
                                } else {
                                    switchMainFragment(this, ChooseOrganizationFragment(person))
                                }
                            }
                        }
                        builder.setNegativeButton(android.R.string.cancel, null)
                        builder.create().show()
                        userId = user.uid
                        Log.d(Constants.TAG, "In the auth listener, user id is ${user.uid}")
                    }
                }
            } else {
                Log.d(Constants.TAG, "Login failed")
                switchMainFragment(this, SplashFragment(this))
            }
        }
    }

    private fun retrieveOrganizationForPerson(person: Person, callback: (Organization?) -> Unit){
        orgRef.get().addOnSuccessListener { snapshot ->
            for (doc in snapshot!!.documents) {
                val organization = Organization.fromSnapshot(doc)
                val foundPerson = organization.members.keys.contains(person.id)
                if (foundPerson) {
                    callback(organization)
                    return@addOnSuccessListener
                }
            }
            callback(null)
        }
    }

    private fun isLoggingIn(uid: String, callback: (Boolean) -> Unit) {
        var personRef = FirebaseFirestore.getInstance().collection("person")
        var x = personRef.whereEqualTo(FieldPath.documentId(), uid)
        x.limit(1).get().addOnSuccessListener {
            callback(!it.isEmpty)
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
