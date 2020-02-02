package edu.rosehulman.orgservicelogger.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.data.retrieveNotification
import edu.rosehulman.orgservicelogger.data.retrieveOrganization
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.events.EventsFragment
import edu.rosehulman.orgservicelogger.notifications.NotificationsFragment
import edu.rosehulman.orgservicelogger.organization.ChooseOrganizationFragment
import edu.rosehulman.orgservicelogger.organization.OrganizationFragment
import edu.rosehulman.orgservicelogger.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment(var userId: String) : Fragment(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var realOrganization: Organization? = null
    private var orgRef = FirebaseFirestore
        .getInstance()
        .collection("organization")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.nav_view.setOnNavigationItemSelectedListener(this)

        val fragment = NotificationsFragment()

        val notificationId = arguments?.getString("notification")
        if (notificationId != null) {
            retrieveNotification(notificationId) { notification ->
                when (notification.type) {
                    Notification.TYPE_CONFIRM -> {
                        fragment.arguments = Bundle().apply {
                            putString("notification", notificationId)
                        }

                        switchToFragment(fragment)
                    }
                    Notification.TYPE_NEEDS_REPLACEMENT, Notification.TYPE_REMINDER -> {
                        switchToFragment(fragment)
                        launchFragment(activity!!, EventFragment(notification.event))
                    }
                }
            }
        } else {
            switchToFragment(fragment)
        }

        return view
    }

    private fun switchToFragment(fragment: NotificationsFragment) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_container, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragmentManager = activity!!.supportFragmentManager
        for (i in 0..fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }

        val fragment = when (menuItem.itemId) {
            R.id.navigation_notifications -> NotificationsFragment()
            R.id.navigation_events -> EventsFragment()
            //TODO: Fix so that the actual organization goes here
            R.id.navigation_organization -> OrganizationFragment(realOrganization!!)
            R.id.navigation_settings -> SettingsFragment()
            else -> TODO("Unimplemented navigation item")
        }

        launchFragment(activity!!, fragment)

        return true
    }

    override fun onResume() {
        super.onResume()
        if (userId != null) {
            retrieveOrganization("soup_kitchen") { organization ->
                realOrganization = organization
            }
            return
        }
        orgRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e(
                    Constants.TAG,
                    "Error in retrieving the organization, exception is $exception"
                )
            }
            for (doc in snapshot!!.documents) {
                val organization = Organization.fromSnapshot(doc)
                var foundPerson = false
                for ((name, bool) in organization.members) {
                    if (name.equals(userId) && bool) {
                        foundPerson = true
                    }
                }
                if (foundPerson) {
                    retrieveOrganization(organization.id!!) { organization ->
                        realOrganization = organization
                    }
                } else { //this is saying that the user is not part of an organization
                    val fragment = ChooseOrganizationFragment()
                    val transaction = activity!!.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.activity_main_frame, fragment)
                    transaction.commit()
                }
            }
        }
    }
}

fun launchFragment(context: FragmentActivity, fragment: Fragment) {
    val transaction = context.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.nav_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
