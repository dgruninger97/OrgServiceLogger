package edu.rosehulman.orgservicelogger.ui.users


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.Person
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.launchFragment

class UserAdapter(var context: FragmentActivity): RecyclerView.Adapter<UserViewHolder>() {
    private var users = ArrayList<Person>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val holder = UserViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.fragment_user_info,
                parent,
                false
            )
        )
        holder.itemView.setOnClickListener {
            launchEvent(holder.adapterPosition)
        }
        return holder
    }

    fun launchEvent(position: Int){
        launchFragment(context,
            UserInfoFragment(users[position])
        )
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(users.get(position))
    }
}