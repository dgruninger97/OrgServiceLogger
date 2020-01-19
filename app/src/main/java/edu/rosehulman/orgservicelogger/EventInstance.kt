package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp

data class EventInstance(
    val base: EventBase,
    val date: Timestamp,
    val people: List<Person>
)
