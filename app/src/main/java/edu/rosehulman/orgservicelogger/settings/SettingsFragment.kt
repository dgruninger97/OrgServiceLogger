package edu.rosehulman.orgservicelogger.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.data.Person
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment() : Fragment() {

    val lambdaChi = Organization("Lambda Chi Alpha", mutableListOf(), listOf(), listOf(), 4)
    val soupKitchen = Organization("Soup Kitchen", mutableListOf(), listOf(), listOf(), 0)
    val chris = Person(
        arrayListOf(lambdaChi),
        arrayListOf(),
        "Chris Gregory",
        "gregorcj@rose-hulman.edu",
        "541 740 7370",
        true,
        arrayListOf(),
        arrayListOf()
    ).also { lambdaChi.members.add(it) }
    val david = Person(
        arrayListOf(lambdaChi, soupKitchen),
        arrayListOf(),
        "David Gruninger",
        "grunindm@rose-hulman.edu",
        "317 605 5636",
        true,
        arrayListOf(),
        arrayListOf()
    ).also { lambdaChi.members.add(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.fragment_settings_org_1_hours.setText("3 / ${david.organizations.get(0).hoursRequirement} hours")
        view.fragment_settings_org_2_hours.setText("4 / ${david.organizations.get(1).hoursRequirement} hours")
        return view
    }
}
