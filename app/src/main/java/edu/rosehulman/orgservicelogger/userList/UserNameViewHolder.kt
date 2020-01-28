package edu.rosehulman.orgservicelogger.userList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_user_name.view.*

class UserNameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var name = view.user_text_view as TextView
}
