package edu.rosehulman.orgservicelogger.organization


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.login.SplashFragment
import kotlinx.android.synthetic.main.fragment_new_organization.view.*

class ChooseOrganizationFragment(private val personId: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_organization, container, false)
        view.fragment_new_organization_create_button.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            val fragment = NewOrganizationFragment(personId)
            transaction.replace(R.id.activity_main_frame, fragment)
            transaction.commit()
        }
        view.fragment_new_organization_logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
        return view
    }
}
