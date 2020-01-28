package edu.rosehulman.orgservicelogger.userList


import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.userInfo.UserInfoFragment

class UserListAdapter(var context: FragmentActivity) : RecyclerView.Adapter<UserNameViewHolder>() {
    // TODO: fix this
    private var users = arrayListOf<Person>() //david, chris)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserNameViewHolder {
        val holder = UserNameViewHolder(
            context.layoutInflater.inflate(
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
