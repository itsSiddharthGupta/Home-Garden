package com.example.homegarden.dataclasses

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.homegarden.BR

class UserProfile : BaseObservable() {
    var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var cityName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var stateName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    var errorMessageFirstName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    var errorMessageLastName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    var errorMessageCityName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    var errorMessageStateName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    @Bindable("valid")
    fun isValid(): Boolean {
        if (firstName.trim().isEmpty()) {
            errorMessageFirstName = "First Name cannot be empty"
            return false
        } else {
            errorMessageFirstName = null
        }
        if (lastName.trim().isEmpty()) {
            errorMessageLastName = "Last Name cannot be empty"
            return false
        } else {
            errorMessageLastName = null
        }
        if (cityName.trim().isEmpty()) {
            errorMessageCityName = "City cannot be empty"
            return false
        } else {
            errorMessageCityName = null
        }
        if (stateName.trim().isEmpty()) {
            errorMessageStateName = "State cannot be empty"
            return false
        } else {
            errorMessageStateName = null
        }
        return true
    }
}