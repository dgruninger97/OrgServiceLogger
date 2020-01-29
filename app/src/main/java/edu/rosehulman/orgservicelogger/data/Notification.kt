package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId

class Notification(
    var event: String = "",
    var type: String = "",
    var owner: String = ""
) {
    @DocumentId
    var id: String? = null

    var personToReplace: String? = null

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
