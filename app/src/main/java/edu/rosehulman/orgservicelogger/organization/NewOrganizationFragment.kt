package edu.rosehulman.orgservicelogger.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.home.HomeFragment
import edu.rosehulman.orgservicelogger.home.switchMainFragment
import kotlinx.android.synthetic.main.fragment_create_new_organization.view.*

class NewOrganizationFragment(private val personId: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_new_organization, container, false)
        view.fragment_create_new_organization_fab.setOnClickListener {
            val name = view.fragment_create_new_organization_name.text.toString()
            val hours = view.fragment_create_new_organization_hours.text.toString().toInt()
            val deadLine = view.fragment_create_new_organization_deadlineLen.text.toString().toInt()

            val organization = Organization(name, mapOf(personId to true), hours, deadLine)
            createOrganization(organization) { organizationId ->
                switchMainFragment(activity!!, HomeFragment(personId, organizationId))
            }
        }
        return view
    }
}