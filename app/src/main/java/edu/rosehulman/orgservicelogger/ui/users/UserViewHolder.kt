package edu.rosehulman.orgservicelogger.ui.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.Person
import kotlinx.android.synthetic.main.view_user_name.view.*

class UserViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var name = view.user_text_view as TextView
}