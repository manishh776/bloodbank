package com.bloodbank.data.repositories

import com.bloodbank.data.network.FirebaseApi
import com.bloodbank.data.network.entities.User
import net.simplifiedcoding.mvvmsampleapp.data.preferences.PreferenceProvider

class UserRepository(
    val firebaseApi: FirebaseApi
){
    fun register(name: String, password: String,
                 gender: String, blood_group: String, mobile: String, token: String){
        firebaseApi.register(name, password, gender, blood_group, mobile, token)
    }
}