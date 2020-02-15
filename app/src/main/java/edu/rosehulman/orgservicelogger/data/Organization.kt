package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot

class Organization(
    var name: String = "",
    var members: MutableMap<String, Boolean> = mutableMapOf(), // person id -> is admin (true if admin, false if normal)
    var hoursRequirement: Int? = null,
    var deadlineLength: Int? = null
) {
    @DocumentId
    var id: String? = null
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Organization {
            val organization = snapshot.toObject(Organization::class.java)!!
            organization.id = snapshot.id
            return organization
        }
    }
}
