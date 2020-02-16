package edu.rosehulman.orgservicelogger.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.events.EventsFragment
import edu.rosehulman.orgservicelogger.notifications.NotificationsFragment
import edu.rosehulman.orgservicelogger.organization.OrganizationFragment
import edu.rosehulman.orgservicelogger.userInfo.UserInfoFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment(private val personId: String, private val organizationId: String) : Fragment(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.nav_view.setOnNavigationItemSelectedListener(this)

        val fragment = NotificationsFragment(personId)
        val notificationId = arguments?.getString(Constants.CLICKED_NOTIFICATION_KEY)
        if (notificationId != null) {
            fragment.arguments = Bundle().apply {
                putString(Constants.CLICKED_NOTIFICATION_KEY, notificationId)
            }
        }
        switchToFragment(fragment)

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
            R.id.navigation_notifications -> NotificationsFragment(personId)
            R.id.navigation_events -> EventsFragment(personId)
            R.id.navigation_organization -> OrganizationFragment(personId, organizationId)
            R.id.navigation_settings -> UserInfoFragment(personId, organizationId)
            else -> TODO("Unimplemented navigation item")
        }

        launchFragment(activity!!, fragment)

        return true
    }

}

fun launchFragment(activity: FragmentActivity, fragment: Fragment) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.nav_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

fun switchMainFragment(activity: FragmentActivity, fragment: Fragment) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.activity_main_frame, fragment)
    transaction.commit()
}