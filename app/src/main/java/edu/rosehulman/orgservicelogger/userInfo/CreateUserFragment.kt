package edu.rosehulman.orgservicelogger.userInfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import kotlinx.android.synthetic.main.fragment_create_new_user.view.*

/**
 * A simple [Fragment] subclass.
 */
class CreateUserFragment(val organizationId: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_create_new_user, container, false)
        view.fragment_create_new_user_fab.setOnClickListener {
            val person = Person()
            person.canDrive = view.fragment_create_new_user_info_checkbox.isChecked
            person.email = view.fragment_create_new_user_email_edit_text.text.toString()
            person.name = view.fragment_create_new_user_name_edit_text.text.toString()
            person.phone = view.fragment_create_new_user_phone_edit_text.text.toString()
            val isOrganizer = view.fragment_create_new_user_organizer_checkbox.isChecked
            val invite = Invite(person, isOrganizer, organizationId)
            addInvite(invite)
            Toast.makeText(context, "User info has been added to the database", Toast.LENGTH_SHORT).show()

        }
        return view
    }


}
