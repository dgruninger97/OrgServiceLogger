package edu.rosehulman.orgservicelogger.ui.organization

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R

data class OrganizationAdapter(var context: Context?): RecyclerView.Adapter<OrganizationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_organization, parent, false)
        return OrganizationViewHolder(view, this)
    }

    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return -1
    }

    override fun onBindViewHolder(holder: OrganizationViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}