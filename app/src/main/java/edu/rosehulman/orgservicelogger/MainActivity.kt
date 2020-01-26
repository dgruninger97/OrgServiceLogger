package edu.rosehulman.orgservicelogger

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import edu.rosehulman.orgservicelogger.ui.events.EventsFragment
import edu.rosehulman.orgservicelogger.ui.notifications.NotificationsFragment
import edu.rosehulman.orgservicelogger.ui.organization.OrganizationFragment
import edu.rosehulman.orgservicelogger.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_container, NotificationsFragment())
        transaction.commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        for (i in 0..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
        when (menuItem.itemId) {
            R.id.navigation_notifications -> {
                launchFragment(NotificationsFragment())
            }
            R.id.navigation_events -> {
                launchFragment(EventsFragment())
            }
            R.id.navigation_organization -> {
                launchFragment(OrganizationFragment())
            }
            R.id.navigation_settings -> {
                launchFragment(SettingsFragment())
            }
        }
        return true
    }

    private fun launchFragment(fragment: Fragment) {
        launchFragment(this, fragment)
    }
}

fun launchFragment(context: FragmentActivity, fragment: Fragment) {
    val transaction = context.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.nav_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
