package com.example.homegarden.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityMyProfileBinding
import com.example.homegarden.dataclasses.UserProfile
import com.example.homegarden.viewmodels.MyProfileActivityViewModel
import com.google.gson.Gson

class MyProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyProfileBinding
    lateinit var viewModel: MyProfileActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_user)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MyProfileActivityViewModel::class.java
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val pref = getSharedPreferences("user", MODE_PRIVATE)
        val userString = pref.getString("profile", null)
        val userProfile = Gson().fromJson(userString, UserProfile::class.java)
        viewModel.init(userProfile)
    }
}