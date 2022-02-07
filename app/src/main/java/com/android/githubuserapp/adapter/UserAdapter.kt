package com.android.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.githubuserapp.R
import com.android.githubuserapp.databinding.ItemRowGithubUserBinding
import com.android.githubuserapp.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter(private var users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
    }

    inner class ItemViewHolder(private val binding: ItemRowGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(80, 80))
                    .error(R.drawable.ic_launcher_background)
                    .into(imgItemAvatar)

                tvItemName.text = user.name
                tvItemCompany.text = user.company
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowGithubUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder) {
            val user: User = users[position]
            bind(user)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(users[position]) }
        }
    }

    override fun getItemCount(): Int = users.size

}