package edu.rosehulman.orgservicelogger.ui.users


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.orgservicelogger.Person

import edu.rosehulman.orgservicelogger.R

/**
 * A simple [Fragment] subclass.
 */
class UserInfoEditFragment(person:Person) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_user_info, container, false)
    }


}
