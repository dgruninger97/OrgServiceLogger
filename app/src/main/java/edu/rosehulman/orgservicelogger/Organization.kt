package edu.rosehulman.orgservicelogger

class Organization(
    var name: String,
    var members: MutableList<Person>,
    var eventBases: List<EventBase>,
    var eventInstances: List<EventInstance>,
    var hoursRequirement: Int?
)
