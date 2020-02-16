package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId

data class Invite(
    var person: Person = Person(),
    var isOrganizer: Boolean = false,
    var organizationId: String = ""
) {
    @DocumentId
    var id: String? = null
}