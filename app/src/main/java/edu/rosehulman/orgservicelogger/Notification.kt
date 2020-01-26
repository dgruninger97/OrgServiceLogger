package edu.rosehulman.orgservicelogger

sealed class Notification(var event: EventInstance)

class ConfirmNotification(event: EventInstance) : Notification(event)
class ReminderNotification(event: EventInstance) : Notification(event)
class NeedsReplacementNotification(event: EventInstance, var person: Person) : Notification(event)
