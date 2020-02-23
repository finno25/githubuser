package com.aura.githubuser.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aura.githubuser.R
import com.aura.githubuser.networks.User
import com.bumptech.glide.Glide
import java.util.*

class UserAdapter(listener: Listener?) :
    RecyclerView.Adapter<ViewHolder>() {
    private val users: ArrayList<User> = ArrayList()
    private var listener: Listener? = null
    fun setUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun clearUsers() {
        this.users.clear()
        notifyDataSetChanged()
    }

    fun addUsers(users: List<User>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        val userHolder = holder as UserHolder
        userHolder.bind(user, position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserHolder(itemView: View) : ViewHolder(itemView) {

        fun bind(user: User, position: Int) {
            itemView.findViewById<TextView>(R.id.tvName).setText(user.login.toLowerCase())

            val imgView = itemView.findViewById<ImageView>(R.id.ivAvatar)

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(imgView)
        }
    }

    interface Listener {
        fun onClickedItem(user: User?)
    }

    init {
        this.listener = listener
    }
}