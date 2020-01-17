package edu.rosehulman.orgservicelogger.ui.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R

class OrganizationFragment : Fragment() {

    private lateinit var organizationViewModel: OrganizationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val linearLayoutView = inflater.inflate(R.layout.fragment_organization, container, false)
        val adapter = OrganizationAdapter(context)
/*        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)*/
        return linearLayoutView
    }
}