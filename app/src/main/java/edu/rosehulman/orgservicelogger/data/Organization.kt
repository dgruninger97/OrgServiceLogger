package edu.rosehulman.orgservicelogger.data

class Organization(
    var name: String,
    var members: MutableList<Person>,
    var eventSeries: List<EventSeries>,
    var eventOccurrences: List<EventOccurrence>,
    var hoursRequirement: Int?
)
