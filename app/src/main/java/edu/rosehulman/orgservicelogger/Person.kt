package edu.rosehulman.orgservicelogger

data class Person(
    val organizations: List<Organization>,
    val administrating: List<Organization>,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val canDrive: Boolean,
    val events: MutableList<EventInstance>,
    val notifications: List<Notification>
)
