package edu.rosehulman.orgservicelogger.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.userInfo.CreateUserFragment
import kotlinx.android.synthetic.main.fragment_user_list.view.*

class UserListFragment(private val organization: Organization) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_user_list, container, false)
        val recyclerView = view.fragment_user_list_recycler_view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = UserListAdapter(activity!!, organization.id!!)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        view.fragment_user_list_fab.setOnClickListener {
            launchFragment(activity!!, CreateUserFragment(organization))
        }
        return view
    }
}
