package edu.rosehulman.orgservicelogger.ui.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.Person
import kotlinx.android.synthetic.main.view_user_name.view.*

class UserViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var user_info = view.user_text_view as TextView
    fun bind(person: Person){
        user_info.setText(person.name)
    }
}