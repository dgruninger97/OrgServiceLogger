package edu.rosehulman.orgservicelogger.ui.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.rosehulman.orgservicelogger.R

class OrganizationFragment : Fragment() {

    private lateinit var organizationViewModel: OrganizationViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        organizationViewModel =
                ViewModelProviders.of(this).get(OrganizationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organization, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        organizationViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}