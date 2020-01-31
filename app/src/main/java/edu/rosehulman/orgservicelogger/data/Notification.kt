package edu.rosehulman.orgservicelogger.data

import android.content.Context
import com.google.firebase.firestore.DocumentId
import edu.rosehulman.orgservicelogger.R

class Notification(
    var event: String = "",
    var type: String = "",
    var person: String = ""
) {
    @DocumentId
    var id: String? = null

    var personToReplace: String? = null

    fun getIconRes(): Int {
        return when (type) {
            TYPE_CONFIRM -> {
                R.drawable.ic_notification_confirm
            }
            TYPE_REMINDER -> {
                R.drawable.ic_notification_reminder
            }
            TYPE_NEEDS_REPLACEMENT -> {
                R.drawable.ic_notification_replacement
            }
            else -> TODO("Unimplemented icon for notification type")
        }
    }

    fun getTitle(context: Context, callback: (String) -> Unit) {
        return when (type) {
            TYPE_CONFIRM ->
                callback(context.getString(R.string.text_notification_confirm))
            TYPE_REMINDER ->
                callback(context.getString(R.string.text_notification_reminder))
            TYPE_NEEDS_REPLACEMENT ->
                retrievePerson(personToReplace!!) { personToReplace ->
                    callback(
                        context.getString(R.string.text_notification_replacement).format(
                            personToReplace.name
                        )
                    )
                }
            else -> TODO("Unimplemented title for notification type")
        }
    }

    companion object {
        const val TYPE_NEEDS_REPLACEMENT = "NEEDS_REPLACEMENT"
        const val TYPE_CONFIRM = "CONFIRM"
        const val TYPE_REMINDER = "REMINDER"

        fun needsReplacement(event: String, person: String, personToReplace: String): Notification {
            return Notification(event, TYPE_NEEDS_REPLACEMENT, person).also {
                it.personToReplace = personToReplace
            }
        }

        fun confirm(event: String, person: String): Notification {
            return Notification(event, TYPE_CONFIRM, person)
        }

        fun reminder(event: String, person: String): Notification {
            return Notification(event, TYPE_REMINDER, person)
        }
    }
}
