package edu.rosehulman.orgservicelogger.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.retrieveOrganizationForPerson
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment(private val personId: String) : Fragment() {
    private lateinit var adapter: EventGroupsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_events, container, false)
        val swipeRefresh = root.fragment_events_swipe_refresh
        swipeRefresh.setOnRefreshListener {
            refreshItems()
        }
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)

        val list = root.fragment_events_list

        adapter = EventGroupsAdapter(context!! as FragmentActivity, personId)
        list.adapter = adapter

        list.layoutManager = LinearLayoutManager(context)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        return root
    }

    override fun onResume() {
        super.onResume()
        refreshItems()
    }

    private fun refreshItems() {
        retrieveOrganizationForPerson(personId) { organizationId ->
            retrieveEventsForOrganization(organizationId!!) { events, serieses ->
                adapter.resetTo(events, serieses)
                view?.also { view -> view.fragment_events_swipe_refresh.isRefreshing = false }
            }
        }
    }
}
