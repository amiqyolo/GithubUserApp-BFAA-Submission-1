package com.android.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateInterpolator
import com.android.githubuserapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initItemAnimate()
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000L)
    }

    private fun initItemAnimate() {
        binding.apply {
            tvText.animate().alpha(1f).alpha(0f).setDuration(3000L).interpolator =
                AccelerateInterpolator()
            lottieLogo.animate().translationY(-3000f).setDuration(1000L).startDelay = 2800L
            progressBar.animate().translationY(3000f).setDuration(1000L).startDelay = 2800L
        }
    }

}