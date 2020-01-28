package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.Exclude

class Organization(
    var name: String = "",
    var members: Map<String, Boolean> = mapOf(), // person id -> is admin (true if admin, false if normal)
    var hoursRequirement: Int? = null
) {
    @get:Exclude
    var id: String? = null
}
