package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.Exclude

class Notification(
    var event: String = "",
    var type: String = "",
    var person: String = ""
) {
    @get:Exclude
    var id: String? = null

    var personToReplace: String? = null
}
