package edu.rosehulman.orgservicelogger.notifications

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.formatEvent
import edu.rosehulman.orgservicelogger.data.retrieveEvent
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import kotlinx.android.synthetic.main.dialog_confirm_event_attendance.view.*
import kotlinx.android.synthetic.main.dialog_edit_event_attendance.view.*
import java.util.concurrent.TimeUnit

object NotificationAction {
    fun performNotification(activity: FragmentActivity, notification: Notification) {
        when (notification.type) {
            Notification.TYPE_CONFIRM -> {
                showConfirmDialog(activity, notification)
            }
            Notification.TYPE_REMINDER, Notification.TYPE_NEEDS_REPLACEMENT -> {
                launchFragment(activity, EventFragment(notification.person, notification.event))
            }
        }
    }

    private fun showConfirmDialog(activity: FragmentActivity, notification: Notification) {
        val dialogView = activity.layoutInflater.inflate(
            R.layout.dialog_confirm_event_attendance,
            null
        )

        notification.getDescription { dialogView.dialog_confirm_event_attendance_event.text = it }

        dialogView.dialog_confirm_event_attendance_time.text =
            "Did you attend for the full time?"

        AlertDialog.Builder(activity)
            .setTitle("Confirm event attendance")
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                // TODO: set in database
                showConfirmationToast(activity)
            }
            .setNeutralButton("Edit") { _, _ ->
                showEditDialog(activity, notification)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create().show()
    }

    private fun showEditDialog(activity: FragmentActivity, notification: Notification) {
        retrieveEvent(notification.event) { event, series ->
            val dialogView = activity.layoutInflater.inflate(
                R.layout.dialog_edit_event_attendance,
                null
            )

            dialogView.dialog_edit_event_attendance_event.text = formatEvent(event, series)
            dialogView.dialog_edit_event_attendance_time_text.text = "How long did you attend for?"

            dialogView.dialog_edit_event_attendance_time_picker.setIs24HourView(true)
            val endTime = series.timeEnd.toDate().time
            val startTime = series.timeStart.toDate().time
            val millis = endTime - startTime
            dialogView.dialog_edit_event_attendance_time_picker.currentHour =
                TimeUnit.MILLISECONDS.toHours(millis).toInt()
            dialogView.dialog_edit_event_attendance_time_picker.currentMinute =
                TimeUnit.MILLISECONDS.toMinutes(millis).toInt() % 60

            AlertDialog.Builder(activity)
                .setTitle("Edit event attendance")
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // TODO: set in database
                    showConfirmationToast(activity)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .create().show()
        }
    }

    private fun showConfirmationToast(context: Context) {
        Toast.makeText(context, "Attendance logged", Toast.LENGTH_SHORT).show()
    }
}