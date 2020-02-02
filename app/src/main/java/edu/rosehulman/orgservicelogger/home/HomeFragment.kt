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
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.data.retrieveOrganization
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

        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_container, NotificationsFragment())
        transaction.commit()

        return view
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
        if(userId.equals("no_login")){
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
                    launchFragment(activity!!, fragment)
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
