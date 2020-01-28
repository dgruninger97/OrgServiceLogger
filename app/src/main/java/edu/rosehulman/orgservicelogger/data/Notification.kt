package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.Exclude

class Notification(
    var event: String = "",
    var type: String = "",
    var owner: String = ""
) {
    @get:Exclude
    var id: String? = null

    var personToReplace: String? = null

    @get:Exclude
    var eventValue: EventOccurrence? = null
    @get:Exclude
    var seriesValue: EventSeries? = null
    @get:Exclude
    var ownerValue: Person? = null
    @get:Exclude
    var personToReplaceValue: Person? = null

    companion object {
        fun needsReplacement(event: String, person: String, personToReplace: String): Notification {
            return Notification(event, "needsReplacement", person).also { it.personToReplace = personToReplace }
        }

        fun confirm(event: String, person: String): Notification {
            return Notification(event, "confirm", person)
        }

        fun reminder(event: String, person: String): Notification {
            return Notification(event, "reminder", person)
        }
    }
}
