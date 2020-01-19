package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp

data class EventBase(
    val name: String,
    val address: String,
    val description: String,
    val organization: Organization,
    val minPeople: Int,
    val maxPeople: Int,
    val timeStart: Timestamp,
    val timeEnd: Timestamp,
    val weeklyRecurrence: List<Boolean>
)
