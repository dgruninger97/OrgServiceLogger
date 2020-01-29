package edu.rosehulman.orgservicelogger.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.retrieveNotifications
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {
    lateinit var adapter: NotificationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val recyclerView = view.fragment_notifications_recycler
        adapter = NotificationsAdapter(activity!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        val swipeRefresh = view.fragment_notifications_swipe_refresh
        swipeRefresh.setOnRefreshListener {
            refreshItems()
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshItems()
    }

    private fun refreshItems() {
        retrieveNotifications("sample_person") {
            adapter.setNotifications(it)
            view?.also { view -> view.fragment_notifications_swipe_refresh.isRefreshing = false }
        }
    }
}
