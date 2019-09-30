package com.bloodbank.data.network.entities

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var id: String? = "",
    var name: String? = "",
    var password: String? = "",
    var gender: String? = "",
    var blood_group: String? = "",
    var mobile: String? = "",
    var token: String? = ""
)
