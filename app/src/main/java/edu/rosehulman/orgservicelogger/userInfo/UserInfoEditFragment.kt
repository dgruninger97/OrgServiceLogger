package edu.rosehulman.orgservicelogger.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.data.writePerson
import kotlinx.android.synthetic.main.fragment_edit_user_info.view.*

class UserInfoEditFragment(var person: Person) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_user_info, container, false)
        view.fragment_edit_user_info_name_edit_text.setText(person.name)
        view.fragment_edit_user_info_email_edit_text.setText(person.email)
        view.fragment_edit_user_info_phone_edit_text.setText(person.phone)

        view.fragment_edit_user_info_fab.setOnClickListener {
            person.name = view.fragment_edit_user_info_name_edit_text.text.toString()
            person.email = view.fragment_edit_user_info_email_edit_text.text.toString()
            person.phone = view.fragment_edit_user_info_phone_edit_text.text.toString()
            writePerson(person)
            activity!!.supportFragmentManager.popBackStack()
            Toast.makeText(context, getString(R.string.text_user_info_updated), Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }
}
