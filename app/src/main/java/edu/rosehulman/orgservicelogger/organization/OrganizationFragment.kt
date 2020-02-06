package edu.rosehulman.orgservicelogger.organization

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.event.AddEventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.userList.UserListFragment
import kotlinx.android.synthetic.main.dialog_edit_organization.view.*
import kotlinx.android.synthetic.main.fragment_organization.view.*

class OrganizationFragment(var organization: Organization) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_organization, container, false)
        view.fragment_organization_name.text = organization.name
        // TODO: hide fab if doesn't have organization edit permissions (see hide method)

        view.fragment_organization_fab.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_organization, null)
            dialogView.dialog_edit_organization_org_name.setText(view.fragment_organization_name.text)
            dialogView.dialog_edit_organization_min_hours.setText(view.fragment_organization_min_hours.text)

            AlertDialog.Builder(context)
                .setTitle("Edit Organization")
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // TODO: Edit organization
                    view.fragment_organization_name.setText(dialogView.dialog_edit_organization_org_name.text)
                    view.fragment_organization_min_hours.setText(dialogView.dialog_edit_organization_min_hours.text)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .create().show()
        }

        view.see_group_members.setOnClickListener {
            launchFragment(
                activity!!,
                UserListFragment(organization.id!!)
            )
        }

        view.fragment_organization_add_event.setOnClickListener {
            launchFragment(activity!!, AddEventFragment())
        }

        return view
    }
}
