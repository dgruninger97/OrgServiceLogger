package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot

class Person(
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var canDrive: Boolean = false
) {
    @DocumentId
    var id: String? = null

}
