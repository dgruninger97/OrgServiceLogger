package edu.rosehulman.orgservicelogger.organization


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.login.SplashFragment
import kotlinx.android.synthetic.main.fragment_new_organization.view.*

/**
 * A simple [Fragment] subclass.
 */
class ChooseOrganizationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_organization, container, false)
        view.fragment_new_organization_yes_button.setOnClickListener {
            launchFragment(activity!!, NewOrganizationFragment())
        }
        view.fragment_new_organization_no_button.setOnClickListener {
//            launchFragment(activity!!, SplashFragment())
        }
        return view
    }


}
