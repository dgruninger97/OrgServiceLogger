package edu.rosehulman.orgservicelogger.ui.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.Person
import kotlinx.android.synthetic.main.user_info.view.*

class UserViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var user_info = view.user_info_text_view as TextView
    var phone_info = view.phone_text_view as TextView
    var email_info = view.email_text_view as TextView
    fun bind(person: Person){
        user_info.setText(user_info.text.toString() + person.firstName + person.lastName)
        phone_info.setText(phone_info.text.toString() + person.phoneNumber)
        email_info.setText(email_info.text.toString() + person.email.toString())
    }
}