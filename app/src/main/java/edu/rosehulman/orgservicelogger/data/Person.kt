package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.Exclude

class Person(
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var canDrive: Boolean = false
) {
    @get:Exclude
    var id: String? = null
}
