package edu.rosehulman.orgservicelogger.userList


import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Organization
import edu.rosehulman.orgservicelogger.data.Person
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.userInfo.UserInfoFragment

class UserListAdapter(var context: FragmentActivity, var organizationId: String) : RecyclerView.Adapter<UserNameViewHolder>() {
    // TODO: fix this
    private var users = mutableListOf<Person>()
    private var userRef = FirebaseFirestore
        .getInstance()
        .collection("organization")
        .document(organizationId)
        .collection("members")
    init{
        userRef.addSnapshotListener { snapshot, exception ->
            if(exception != null){
                Log.d(Constants.TAG, "Error retreiving the users, exception: $exception")
                return@addSnapshotListener
            }
            users = snapshot!!.toObjects(Person::class.java)
        }
    }

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
