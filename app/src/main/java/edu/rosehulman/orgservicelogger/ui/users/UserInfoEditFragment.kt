package edu.rosehulman.orgservicelogger.ui.users


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import edu.rosehulman.orgservicelogger.Person

import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_edit_user_info.view.*



/**
 * A simple [Fragment] subclass.
 */
class UserInfoEditFragment(var person:Person) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_user_info, container, false)
        view.fragment_edit_user_info_name_edit_text.setText(person.name)
        view.fragment_edit_user_info_email_edit_text.setText(person.email)
        view.fragment_edit_user_info_phone_edit_text.setText(person.phoneNumber)
        view.fragment_edit_user_info_fab.setOnClickListener {
            changePerson(view.fragment_edit_user_info_name_edit_text.text.toString(), view.fragment_edit_user_info_email_edit_text.text.toString(), view.fragment_edit_user_info_phone_edit_text.text.toString())
            val toast = Toast.makeText(context, "User info has been updated", Toast.LENGTH_SHORT)
            toast.show()
        }
        return view
    }

    fun changePerson(newName:String, newEmail:String, newPhone:String){
        person.name = newName
        person.email = newEmail
        person.phoneNumber = newPhone
    }


}
