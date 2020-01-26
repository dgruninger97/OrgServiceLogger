package edu.rosehulman.orgservicelogger.ui.users


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.*

class UserListAdapter(var context: FragmentActivity) : RecyclerView.Adapter<UserViewHolder>() {
    private var users = ArrayList<Person>()

    init {
        val lambdaChi = Organization("Lambda Chi Alpha", listOf(), listOf(), listOf(), 4)
        val david = Person(
            arrayListOf(lambdaChi),
            arrayListOf(),
            "David Gruninger",
            "grunindm@rose-hulman.edu",
            "317 605 5636",
            true,
            arrayListOf(),
            arrayListOf()
        )
        val chris = Person(
            arrayListOf(lambdaChi),
            arrayListOf(),
            "Chris Gregory",
            "gregorcj@rose-hulman.edu",
            "541 740 7370",
            true,
            arrayListOf(),
            arrayListOf()
        )
        users.add(david)
        users.add(chris)
        notifyDataSetChanged()
        Log.d(Constants.TAG, "$users")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        Log.d(Constants.TAG, "Creating vh")
        val holder = UserViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_user_name,
                parent,
                false
            )
        )
        holder.itemView.setOnClickListener {
            launchEvent(holder.adapterPosition)
        }
        return holder
    }

    fun launchEvent(position: Int) {
        launchFragment(
            context,
            UserInfoFragment(users[position])
        )
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d(Constants.TAG, "Calling on Bind")
        holder.bind(users.get(position))
    }
}