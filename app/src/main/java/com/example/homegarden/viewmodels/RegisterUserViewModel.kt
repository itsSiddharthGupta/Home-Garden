package com.example.homegarden.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.dataclasses.UserProfile

class RegisterUserViewModel : ViewModel() {
    var userProfile: UserProfile = UserProfile()
    var user: MutableLiveData<UserProfile> = MutableLiveData()
    init {
        user.value = null
    }
    fun onClick(){
        if(userProfile.isValid()){
            user.value = userProfile
        }
    }
}