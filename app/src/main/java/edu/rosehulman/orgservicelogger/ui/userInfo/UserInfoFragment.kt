package edu.rosehulman.orgservicelogger.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.Person
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.launchFragment
import kotlinx.android.synthetic.main.fragment_user_info.view.*

class UserInfoFragment(var person: Person) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)
        view.fragment_user_info_name_text_view.text = person.name
        view.fragment_user_info_drive_text_view.text =
            if (person.canDrive) {
                "Can drive to events"
            } else {
                "Can't drive to events"
            }
        view.fragment_user_info_email_text_view.text = person.email
        view.fragment_user_info_phone_text_view.text = person.phoneNumber
        view.user_info_edit_fab.setOnClickListener {
            launchFragment(activity!!, UserInfoEditFragment(person))
        }
        return view
    }
}