package com.suitmediatest.ui.third_screen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suitmediatest.INTENT_KEY
import com.suitmediatest.R
import com.suitmediatest.data.User
import com.suitmediatest.ui.second_screen.SecondActivity

class ThirdAdapter : PagingDataAdapter<User, ThirdAdapter.UserViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position) as User
        holder.bind(user)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            Glide.with(itemView)
                .load(user.avatar)
                .into(itemView.findViewById(R.id.circle_image))
            val fullname = String.format(
                itemView.context.getString(R.string.full_name_format),
                user.first_name,
                user.last_name
            )
            itemView.findViewById<TextView>(R.id.tv_name).text = fullname
            itemView.findViewById<TextView>(R.id.tv_email).text = user.email
            itemView.setOnClickListener {
                val intentToSecond = Intent(itemView.context, SecondActivity::class.java)
                intentToSecond.putExtra(INTENT_KEY, fullname)
                itemView.context.startActivity(intentToSecond)
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }

    }

}