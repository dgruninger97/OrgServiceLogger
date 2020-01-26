package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.util.*

val lambdaChi = Organization("Lambda Chi Alpha", listOf(), listOf(), listOf(), 4)
val ryvesHallBase = EventBase(
    "Ryves Hall",
    "1356 Locust St, Terre Haute, IN 47807",
    "Volunteer at the local Ryves Hall youth center.  This often involves light physical activity such as installing wiring, setting up for events, or painting.",
    lambdaChi,
    2,
    2,
    Timestamp(Date(119, 11, 12, 17, 30, 0)), // 12/19/2019 5:30:00pm
    Timestamp(Date(119, 11, 12, 19, 30, 0)), // 12/19/2019 7:30:00pm,
    listOf(false, false, false, false, true, false, false) // Sunday, ..., Saturday
)
val chris = Person(
    arrayListOf(lambdaChi),
    arrayListOf(),
    "Chris Gregory",
    "gregorcj@rose-hulman.edu",
    "541 740 7370",
    true,
    arrayListOf(),
    arrayListOf()
)
val david = Person(
    arrayListOf(lambdaChi),
    arrayListOf(),
    "David Gruninger",
    "grunindm@rose-hulman.edu",
    "317 605 5636",
    true,
    arrayListOf(),
    arrayListOf()
)
val events = listOf(
    EventInstance(
        ryvesHallBase,
        Timestamp(Date(119, 11, 12)),
        arrayListOf(chris, david)
    ).also { chris.events.add(it); david.events.add(it) },
    EventInstance(
        ryvesHallBase,
        Timestamp(Date(119, 11, 19)),
        arrayListOf(chris)
    ).also { chris.events.add(it) }
)
