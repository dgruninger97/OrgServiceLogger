package edu.rosehulman.orgservicelogger

data class Person(
    var organizations: List<Organization>,
    var administrating: List<Organization>,
    var name: String,
    var email: String,
    var phoneNumber: String,
    var canDrive: Boolean,
    var events: MutableList<EventInstance>,
    var notifications: List<Notification>
)
