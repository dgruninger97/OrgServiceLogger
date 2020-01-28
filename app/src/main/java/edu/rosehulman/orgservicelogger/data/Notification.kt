package edu.rosehulman.orgservicelogger.data

sealed class Notification(var event: EventOccurrence)

class ConfirmNotification(event: EventOccurrence) : Notification(event)
class ReminderNotification(event: EventOccurrence) : Notification(event)
class NeedsReplacementNotification(event: EventOccurrence, var person: Person) : Notification(event)
