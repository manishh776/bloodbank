package com.bloodbank.data.network

import android.util.Log
import android.widget.Toast
import com.bloodbank.data.network.entities.User
import com.bloodbank.data.sqlite.Config
import com.google.firebase.database.*

class FirebaseApi(
    private var database: DatabaseReference
){
    fun register(name: String, password: String,
                 gender: String, blood_group: String, mobile: String, token: String){
        val id = database.push().key
        val user = User(id, name, password, gender, blood_group, mobile, token)
        database.child(Config.FIREBASE_DATABASE_USERS).child(id!!).setValue(user)
    }
}