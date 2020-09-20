package com.example.homegarden.activities

import android.os.Parcel
import android.os.Parcelable

class UserProfile(var firstName: String, var lastName: String, var city: String, var state: String) : Parcelable {
    private constructor(parcel: Parcel) : this(
        firstName = parcel.readString()!!,
        lastName = parcel.readString()!!,
        city = parcel.readString()!!,
        state = parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeString(firstName)
        dest?.writeString(lastName)
        dest?.writeString(city)
        dest?.writeString(state)
    }

    companion object CREATOR : Parcelable.Creator<UserProfile> {
        override fun createFromParcel(parcel: Parcel): UserProfile {
            return UserProfile(parcel)
        }

        override fun newArray(size: Int): Array<UserProfile?> {
            return arrayOfNulls(size)
        }
    }
}