package edu.rosehulman.orgservicelogger.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.retrieveNotification
import edu.rosehulman.orgservicelogger.data.retrieveNotifications
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment(private val personId: String) : Fragment() {
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

        // Notification is clicked and we have to deal with it
        val notificationId = arguments?.getString(Constants.CLICKED_NOTIFICATION_KEY)
        if (notificationId != null) {
            retrieveNotification(notificationId) { notification ->
                NotificationAction.performNotification(activity!!, notification)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshItems()
    }

    private fun refreshItems() {
        retrieveNotifications(personId) {
            adapter.setNotifications(it)
            view?.also { view -> view.fragment_notifications_swipe_refresh.isRefreshing = false }
        }
    }
}
