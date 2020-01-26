package edu.rosehulman.orgservicelogger.ui.users

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.orgservicelogger.Person
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.launchFragment
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*

class UserInfoFragment(var person: Person) : Fragment(){
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)
        view.fragment_user_info_name_text_view.text = view.fragment_user_info_name_text_view.text.toString() + person.name
        var canDriveString = "Can drive to events"
        if(!person.canDrive){
            canDriveString = "Can't drive to events"
        }
        view.fragment_user_info_drive_text_view.text = canDriveString
        view.fragment_user_info_email_text_view.text = view.fragment_user_info_email_text_view.text.toString() + person.email
        view.fragment_user_info_phone_text_view.text = view.fragment_user_info_phone_text_view.text.toString() + person.phoneNumber
        view!!.user_info_edit_fab.setOnClickListener {
            launchFragment(activity!!, UserInfoEditFragment(person))
        }
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
//        listener = null
    }
//
//
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
}
