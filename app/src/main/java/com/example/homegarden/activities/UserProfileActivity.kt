package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityUserProfileBinding
import com.google.gson.Gson

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.submit.setOnClickListener { saveUserDetails() }
    }

    private fun saveUserDetails(): Unit {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val city = binding.city.text.toString()
        val state = binding.state.text.toString()
        if (firstName.trim().isEmpty()) {
            binding.inputFirstName.error = "First Name should not be empty."
            return
        } else {
            binding.inputFirstName.error = null
        }
        if (lastName.trim().isEmpty()) {
            binding.inputLastName.error = "Last Name should not be empty."
            return
        } else {
            binding.inputLastName.error = null
        }
        if (city.trim().isEmpty()) {
            binding.inputCity.error = "City should not be empty."
            return
        } else {
            binding.inputCity.error = null
        }
        if (state.trim().isEmpty()) {

            binding.inputState.error = "State should not be empty."
            return
        } else {
            binding.inputState.error = null
        }
        val user = UserProfile(firstName, lastName, city, state)
        val pref = getSharedPreferences("user", MODE_PRIVATE)
        pref.edit().putString("profile", Gson().toJson(user)).apply()
        startActivity(Intent(this, HomeActivity::class.java))
    }
}