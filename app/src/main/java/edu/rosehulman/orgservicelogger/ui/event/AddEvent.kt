package edu.rosehulman.orgservicelogger.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.launchFragment
import kotlinx.android.synthetic.main.fragment_event.view.*
import java.text.SimpleDateFormat

class AddEvent() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_event, container, false)
        return view
    }
}