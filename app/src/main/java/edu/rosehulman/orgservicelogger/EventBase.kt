package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp

data class EventBase(
    val name: String,
    val address: String,
    val description: String,
    val timeStart: Timestamp,
    val timeEnd: Timestamp,
    val weeklyRecurrence: List<Boolean>
)
