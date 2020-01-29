package edu.rosehulman.orgservicelogger.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        // TODO: fix this
//        view.fragment_settings_org_1_hours.setText("3 / ${david.organizations.get(0).hoursRequirement} hours")
//        view.fragment_settings_org_2_hours.setText("4 / ${david.organizations.get(1).hoursRequirement} hours")
        return view
    }
}
