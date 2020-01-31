package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot

class Organization(
    var name: String = "",
    var members: Map<String, Boolean> = mapOf(), // person id -> is admin (true if admin, false if normal)
    var hoursRequirement: Int? = null
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
