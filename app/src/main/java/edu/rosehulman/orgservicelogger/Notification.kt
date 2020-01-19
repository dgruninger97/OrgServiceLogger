package edu.rosehulman.orgservicelogger

sealed class Notification(val event: EventInstance) {}

class ConfirmNotification(event: EventInstance) : Notification(event) {}
class ReminderNotification(event: EventInstance) : Notification(event) {}
class NeedsReplacementNotification(event: EventInstance, val person: Person) : Notification(event) {}
