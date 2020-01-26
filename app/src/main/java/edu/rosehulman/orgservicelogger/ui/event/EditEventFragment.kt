package edu.rosehulman.orgservicelogger.ui.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.formatDate
import edu.rosehulman.orgservicelogger.formatTimeSpan
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
        view.fragment_edit_event_date.text = formatDate(date)
        view.fragment_edit_event_time.text = formatTimeSpan(timeStart, timeEnd)

        view.fragment_edit_event_date.setOnClickListener {
            DatePickerDialog(
                context!!,
                { _, year, month, day ->
                    date = Timestamp(Date(year - 1900, month, day))
                    view.fragment_edit_event_date.text = formatDate(date)
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        view.fragment_edit_event_time.setOnClickListener {
            showTimePicker(timeStart.toDate()) { start ->
                showTimePicker(timeEnd.toDate()) { end ->
                    if (end.toDate().before(start.toDate())) {
                        Snackbar.make(
                            view.fragment_edit_event_coordinator,
                            "End must be after start",
                            Snackbar.LENGTH_LONG
                        ).show()
                        return@showTimePicker
                    }
                    timeStart = start
                    timeEnd = end
                    view.fragment_edit_event_time.text = formatTimeSpan(timeStart, timeEnd)
                }
            }
        }

        view.fragment_edit_event_fab.setOnClickListener {
            event.date = date
            event.base.timeStart = timeStart
            event.base.timeEnd = timeEnd
            activity!!.supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun showTimePicker(date: Date, callback: (Timestamp) -> Unit) {
        TimePickerDialog(
            context!!,
            { _, hour, minute ->
                callback(Timestamp(Date(0, 0, 0, hour, minute, 0)))
            },
            date.hours,
            date.minutes,
            DateFormat.is24HourFormat(context)
        )
            .show()
    }
}
