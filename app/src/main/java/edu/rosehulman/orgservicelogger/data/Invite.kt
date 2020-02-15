package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId

data class Invite(val person: Person, val isOrganizer: Boolean) {
    @DocumentId
    var id: String? = null
}