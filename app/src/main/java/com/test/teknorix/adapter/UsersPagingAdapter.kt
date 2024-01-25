package com.test.teknorix.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.teknorix.R
import com.test.teknorix.repository.model.User
import com.squareup.picasso.Picasso

class UsersPagingAdapter(private val clickListener: ClickListener) : PagingDataAdapter<User, UsersPagingAdapter.MyVH>(
    COMPARATOR
) {

    interface ClickListener {
        fun onClick(user: User, position: Int)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val firstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val avatar: AppCompatImageView = itemView.findViewById(R.id.ivAvatar)
        init {
            if (clickListener != null) {
                itemView.setOnClickListener(this)
            }
        }
        override fun onClick(view: View?) {
            val position = bindingAdapterPosition
            val item = getItem(position)
            if (position != RecyclerView.NO_POSITION){
                item?.let { clickListener.onClick(it, position) }
            }
        }
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            val fullName = item.first_name + " " + item.last_name
            holder.firstName.text = fullName
            holder.email.text = item.email
            Picasso.get()
                .load(item.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(holder.avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MyVH(itemView)
    }
}