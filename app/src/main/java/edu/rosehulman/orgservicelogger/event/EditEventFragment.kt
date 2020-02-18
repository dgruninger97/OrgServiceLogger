package edu.rosehulman.orgservicelogger.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import kotlinx.android.synthetic.main.fragment_edit_event.view.*
import kotlinx.android.synthetic.main.view_edit_recurrence_day.view.*
import java.time.ZoneId
import java.util.*

class EditEventFragment(eventId: String?, organizationId: String?) : Fragment() {
    private val weekDays =
        listOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

    private var event = EventOccurrence().also { it.id = eventId }
    private var series = EventSeries().also {
        if (organizationId != null) {
            it.organization = organizationId
        }
    }

    private val recurrences = listOf(
        Recurrence("S"),
        Recurrence("M"),
        Recurrence("T"),
        Recurrence("W"),
        Recurrence("H"),
        Recurrence("F"),
        Recurrence("S")
    )

    class Recurrence(val title: String) {
        lateinit var checkBox: CheckBox
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = if (event.id == null) {
            inflater.inflate(R.layout.fragment_add_event, container, false)
        } else {
            inflater.inflate(R.layout.fragment_edit_event, container, false)
        }

        val eventId = event.id
        if (eventId != null) {
            retrieveEvent(eventId) { event, series ->
                this.event = event
                this.series = series

                bindView(view)
                bindRecurrences(view)
            }
        } else {
            for (weekday in weekDays) {
                series.weeklyRecurrence[weekday] = false
            }
            bindRecurrences(view)
        }

        addListeners(view)

        return view
    }

    private fun bindView(view: View) {
        view.fragment_edit_event_name.setText(series.name)
        view.fragment_edit_event_address.setText(series.address)
        view.fragment_edit_event_description.setText(series.description)
        view.fragment_edit_event_time.text = formatTimeSpan(series.timeStart, series.timeEnd)
        view.fragment_edit_event_date.text = formatDate(event.date)
    }

    private fun bindRecurrences(view: View) {
        view.fragment_edit_event_weekly_recurrence_container.removeAllViews()
        for ((index, recurrence) in recurrences.withIndex()) {
            val recurrenceView = layoutInflater.inflate(R.layout.view_edit_recurrence_day, null)
            recurrenceView.view_edit_recurrence_day_text.text = recurrence.title
            recurrence.checkBox = recurrenceView.view_edit_recurrence_day_check
            recurrence.checkBox.isChecked = series.weeklyRecurrence.containsKey(weekDays[index])

            recurrence.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    series.weeklyRecurrence[weekDays[index]] = true
                } else {
                    series.weeklyRecurrence.remove(weekDays[index])
                }
            }

            view.fragment_edit_event_weekly_recurrence_container.addView(recurrenceView)
        }
    }

    private fun addListeners(view: View) {
        view.fragment_edit_event_date.setOnClickListener {
            DatePickerDialog(
                context!!,
                { _, year, month, day ->
                    event.date = Timestamp(Date(year - 1900, month, day))
                    view.fragment_edit_event_date.text = formatDate(event.date)
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        view.fragment_edit_event_time.setOnClickListener {
            showTimePicker(series.timeStart.toDate()) { start ->
                showTimePicker(series.timeEnd.toDate()) { end ->
                    if (end.toDate().before(start.toDate())) {
                        Snackbar.make(
                            view.fragment_edit_event_coordinator,
                            getString(R.string.text_event_error_end_after_start),
                            Snackbar.LENGTH_LONG
                        ).show()
                        return@showTimePicker
                    }
                    series.timeStart = start
                    series.timeEnd = end
                    view.fragment_edit_event_time.text =
                        formatTimeSpan(series.timeStart, series.timeEnd)
                }
            }
        }

        if (event.id != null) {
            view.fragment_edit_event_delete_button.setOnClickListener {
                removeEvent(event.id!!)
            }
        }
        
        view.fragment_edit_event_fab.setOnClickListener {
            series.name = view.fragment_edit_event_name.text.toString()
            series.address = view.fragment_edit_event_address.text.toString()
            series.description = view.fragment_edit_event_description.text.toString()
            series.maxPeople = view.fragment_edit_event_max.text.toString().toInt()
            series.minPeople = view.fragment_edit_event_min.text.toString().toInt()
            if (event.id == null) {
                createEvent(series, event)
            } else {
                writeEventOccurrence(event)
                writeEventSeries(series)
            }
            activity!!.supportFragmentManager.popBackStack()
            Toast.makeText(context, getString(R.string.text_event_updated), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showTimePicker(date: Date, callback: (Timestamp) -> Unit) {
        TimePickerDialog(
            context!!,
            { _, hour, minute ->
                callback(Timestamp(Date(70, 0, 1, hour, minute, 0)))
            },
            date.hours,
            date.minutes,
            DateFormat.is24HourFormat(context)
        )
            .show()
    }
}
