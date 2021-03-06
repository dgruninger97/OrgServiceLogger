package edu.rosehulman.orgservicelogger.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.retrieveIsOrganizer
import edu.rosehulman.orgservicelogger.data.retrievePerson
import edu.rosehulman.orgservicelogger.home.launchFragment
import kotlinx.android.synthetic.main.fragment_user_info.view.*

class UserInfoFragment(private val userId: String, private val personId: String, private val organizationId: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)

        retrievePerson(personId) { person ->
            view.fragment_user_info_name_text_view.text = person.name
            view.fragment_user_info_drive_text_view.text =
                if (person.canDrive) {
                    getString(R.string.text_can_drive)
                } else {
                    getString(R.string.text_can_not_drive)
                }
            view.fragment_user_info_email_text_view.text = person.email
            view.fragment_user_info_phone_text_view.text = person.phone
            view.fragment_user_info_edit_fab.setOnClickListener {
                launchFragment(activity!!, UserInfoEditFragment(person, organizationId))
            }

            if (userId == personId) {
                view.fragment_user_info_edit_fab.show()
            } else {
                retrieveIsOrganizer(userId, organizationId) { isOrganizer ->
                    if (isOrganizer) {
                        view.fragment_user_info_edit_fab.show()
                    }
                }
            }
        }

        return view
    }
}
