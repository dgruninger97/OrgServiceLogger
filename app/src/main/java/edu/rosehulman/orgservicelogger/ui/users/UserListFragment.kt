package edu.rosehulman.orgservicelogger.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R

class UserListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var userAdapter = UserListAdapter(activity!!)
        val recycler_view = inflater.inflate(R.layout.fragment_user_list, container,false) as RecyclerView
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = userAdapter
        Log.d(Constants.TAG, "Adapter getting set")
        return recycler_view
    }

}