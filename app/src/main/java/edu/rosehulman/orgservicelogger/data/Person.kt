package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.DocumentId

class Person(
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var canDrive: Boolean = false
) {
    @DocumentId
    var id: String? = null

}
