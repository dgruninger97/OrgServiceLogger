package edu.rosehulman.orgservicelogger.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.data.addMemberToOrganization
import edu.rosehulman.orgservicelogger.data.retrieveInviteExists
import edu.rosehulman.orgservicelogger.data.retrieveOrganizationForPerson
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import edu.rosehulman.orgservicelogger.notifications.NotificationLauncher
import edu.rosehulman.orgservicelogger.organization.ChooseOrganizationFragment

/**
 * A simple [Fragment] subclass.
 */
class LoggedInSplashFragment(val person: Person) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            NotificationLauncher.scheduleNotifications(context!!, person.id!!)
            retrieveOrganizationForPerson(person.id!!) { organization ->
                if (organization != null) {
                    Log.d(
                        Constants.TAG,
                        "Person ${person.id!!} is a member of organization: $organization"
                    )
                    switchMainFragment(activity!!, HomeFragment(person.id!!, organization))
                } else {
                    retrieveInviteExists(person.email!!) { invite ->
                        if (invite != null) {
                            Log.d(Constants.TAG, "Logging in as ${person.id}")
                            addMemberToOrganization(
                                invite.organizationId,
                                person.id!!,
                                invite.isOrganizer
                            )
                            switchMainFragment(activity!!, HomeFragment(person.id!!, invite.organizationId))
                        } else {
                            Log.d(
                                Constants.TAG,
                                "Person ${person.id!!} is a member of no organizations"
                            )
                            switchMainFragment(activity!!, ChooseOrganizationFragment(person.id!!))
                        }
                    }
                }
            }
        return inflater.inflate(R.layout.fragment_logged_in_splash, null, false)
    }


}
