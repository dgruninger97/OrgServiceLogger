package edu.rosehulman.orgservicelogger.ui.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_edit_event.view.*
import java.util.*

class EditEventFragment(val event: EventInstance) : Fragment() {
    var date = event.date
    var timeStart = event.base.timeStart
    var timeEnd = event.base.timeEnd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_event, container, false)
        view.fragment_edit_event_name.setText(event.base.name)
        view.fragment_edit_event_address.setText(event.base.address)
        view.fragment_edit_event_description.setText(event.base.description)
        view.fragment_edit_event_date.text = event.formatDate()
        view.fragment_edit_event_time.text = event.base.formatTime()

        view.fragment_edit_event_date.setOnClickListener {
            DatePickerDialog(
                context!!,
                { _, year, month, date ->
                    event.date = Timestamp(Date(year - 1900, month, date))
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        view.fragment_edit_event_time.setOnClickListener {
            showTimePicker(timeStart.toDate(), {
                showTimePicker(timeEnd.toDate(), {})
            })
        }

        view.fragment_edit_event_fab.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun showTimePicker(date: Date, callback: () -> Unit) {
        TimePickerDialog(
            context!!,
            { _, hour, minute ->
                timeStart = Timestamp(Date(0, 0, 0, hour, minute, 0))
                callback()
            },
            date.hours,
            date.minutes,
            DateFormat.is24HourFormat(context)
        )
            .show()
    }
}
