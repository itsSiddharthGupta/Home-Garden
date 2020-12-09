package com.example.homegarden.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.dataclasses.UserProfile

class MyProfileActivityViewModel: ViewModel(){
    val userProfile: MutableLiveData<UserProfile> = MutableLiveData<UserProfile>()

    fun init(userProfile: UserProfile){
        this.userProfile.value = userProfile
    }
}