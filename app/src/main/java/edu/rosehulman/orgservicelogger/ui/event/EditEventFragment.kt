package edu.rosehulman.orgservicelogger.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_edit_event.view.*

class EditEventFragment(val event: EventInstance) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_event, container, false)
//        view.fragment_edit_event_date.setText(event.date)
        view.fragment_edit_event_address.setText(event.base.address)
        view.fragment_edit_event_fab.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
        return view
    }
}
