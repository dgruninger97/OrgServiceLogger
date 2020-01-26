package edu.rosehulman.orgservicelogger

data class Organization(
    val name: String,
    val members: MutableList<Person>,
    val eventBases: List<EventBase>,
    val eventInstances: List<EventInstance>,
    val hoursRequirement: Int?
)
