package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId

data class Invite(val person: Person, val isOrganizer: Boolean, val organizationId:String) {
    @DocumentId
    var id: String? = null
}