package edu.rosehulman.orgservicelogger.userInfo

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.MainActivity
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.data.removeMemberFromOrganization
import edu.rosehulman.orgservicelogger.data.writePerson
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import edu.rosehulman.orgservicelogger.login.LoggedInSplashFragment
import edu.rosehulman.orgservicelogger.login.SplashFragment
import kotlinx.android.synthetic.main.fragment_edit_user_info.view.*

class UserInfoEditFragment(var person: Person, var organizationId:String) : Fragment() {
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
        view.fragment_edit_user_info_leave_group_button.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Are you sure that you want to leave this organization? This action cannot be undone")
            builder.setPositiveButton(android.R.string.yes){ dialog: DialogInterface?, which: Int ->
                removeMemberFromOrganization(organizationId, person.id!!).continueWith{
                    switchMainFragment(activity!!, LoggedInSplashFragment(person))
                }
            }
            builder.setNegativeButton(android.R.string.cancel,null)

            builder.create().show()
        }
        return view
    }

}


