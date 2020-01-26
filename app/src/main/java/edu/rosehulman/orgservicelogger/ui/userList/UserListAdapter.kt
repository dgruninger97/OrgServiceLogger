package edu.rosehulman.orgservicelogger.ui.userList


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.*
import edu.rosehulman.orgservicelogger.ui.userInfo.UserInfoFragment

class UserListAdapter(var context: FragmentActivity) : RecyclerView.Adapter<UserNameViewHolder>() {
    private var users = arrayListOf(david, chris)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNameViewHolder {
        val holder = UserNameViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_user_name,
                parent,
                false
            )
        )
        holder.itemView.setOnClickListener {
            showUserInfo(holder.adapterPosition)
        }
        return holder
    }

    private fun showUserInfo(position: Int) {
        launchFragment(context, UserInfoFragment(users[position]))
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserNameViewHolder, position: Int) {
        holder.name.text = users[position].name
    }
}