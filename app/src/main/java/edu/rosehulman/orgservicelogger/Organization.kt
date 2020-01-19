package edu.rosehulman.orgservicelogger

data class Organization(
    val name: String,
    val members: List<Person>,
    val eventBases: List<EventBase>,
    val eventInstances: List<EventInstance>,
    val hoursRequirement: Int?
)
