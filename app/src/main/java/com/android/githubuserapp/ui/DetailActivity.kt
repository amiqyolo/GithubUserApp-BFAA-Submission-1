package com.android.githubuserapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.githubuserapp.databinding.ActivityDetailBinding
import com.android.githubuserapp.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USER = "DetailActivity.EXTRA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        setToolbar()
        bind(user, this)
    }

    private fun setToolbar() {
        if (supportActionBar != null) {
            this.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                title = "Detail Profile"
                elevation = 0f
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun bind(user: User, context: Context) {
        binding.apply {
            tvDetailName.text = user.name
            tvDetailUsername.text = user.username
            tvDetailFollower.text = user.follower.toString()
            tvDetailFollowing.text = user.following.toString()
            tvDetailRepository.text = user.repository.toString()
            tvDetailCompany.text = user.company
            tvDetailLocation.text = user.location

            Glide.with(context)
                .load(user.avatar)
                .apply(
                    RequestOptions()
                        .circleCrop()
                        .override(120, 120)
                )
                .into(ivDetailAvatar)
        }
    }

}