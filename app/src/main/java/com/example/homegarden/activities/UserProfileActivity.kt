package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityUserProfileBinding
import com.example.homegarden.viewmodels.UserProfileViewModel
import com.google.gson.Gson

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                UserProfileViewModel::class.java
            )
        binding.userProfileViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.user.observe(this, Observer { saveUserDetails(it) })
    }

    private fun saveUserDetails(userProfile: UserProfile?): Unit {
        if (userProfile != null) {
            Log.d("UserProfile", userProfile.toString())
            val pref = getSharedPreferences("user", MODE_PRIVATE)
            pref.edit().putString("profile", Gson().toJson(userProfile)).apply()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
