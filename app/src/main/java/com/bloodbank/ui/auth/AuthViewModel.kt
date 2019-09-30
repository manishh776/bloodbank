package com.bloodbank.ui.auth

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModel
import com.bloodbank.data.network.entities.User
import com.bloodbank.data.repositories.UserRepository
import com.bloodbank.data.sqlite.Config
import com.bloodbank.data.sqlite.KeyValueDb
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel(
   private val userRepository: UserRepository,
   private val context: Context
) : ViewModel() {

    var first_name: String? = null
    var last_name: String? = null
    var password: String? = null
    var confirm_password: String? = null
    var male: Boolean? = null
    var female: Boolean? = null
    var blood_group: String? = null
    var mobile: String? = null
    var checkbox_terms: Boolean? = null
    var authListener: AuthListener? = null
    val empty_error_message: String = " can't be empty"
    var bank: Boolean? = null
    var database = FirebaseDatabase.getInstance().reference.child(Config.FIREBASE_DATABASE_USERS)

    fun openSignUp(view: View){
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onRegister(view : View){
        validate()
    }

    fun validate(){
        if(TextUtils.isEmpty(first_name)){
            authListener?.onInputError("First name" + empty_error_message)
            return
        }
        if(TextUtils.isEmpty(last_name)){
            authListener?.onInputError("Last name" + empty_error_message)
            return
        }
        if(TextUtils.isEmpty(password)){
            authListener?.onInputError("Password" + empty_error_message)
            return
        }
        if(TextUtils.isEmpty(confirm_password)){
            authListener?.onInputError("Confirm Password" + empty_error_message)
            return
        }
        if(male == null && female == null){
            authListener?.onInputError("Please select gender")
            return
        }
        if(TextUtils.isEmpty(blood_group)){
            authListener?.onInputError("Blood Group" + empty_error_message)
            return
        }
        if(TextUtils.isEmpty(mobile)){
            authListener?.onInputError("Mobile" + empty_error_message)
            return
        }
        if(!checkbox_terms!!){
            authListener?.onInputError("Please accept the terms and conditions")
            return
        }
        if(!password.equals(confirm_password)){
            authListener?.onInputError("Password and Confirm password do not match")
        }
        checkifMobileExists()
    }

    fun loginUser(view: View){
        if(mobile!!.isEmpty() || password!!.isEmpty()){
            authListener?.onInputError("Invalid details")
        }
        authListener?.onLoginStarted()
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(ds  in dataSnapshot.children) {
                    val user = ds.getValue(User::class.java)
                    if (user != null && user.mobile.equals(mobile) && user.password.equals(password)) {
                        authListener?.onLoginSuccess()
                        updateToken(user.id)
                        return
                    }
                }
                authListener?.onLoginFailed()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addListenerForSingleValueEvent(userListener)
    }

    private fun updateToken(id: String?) {
        val token = KeyValueDb.get(context, Config.TOKEN, "")
        database.child(id!!).child("token").setValue(token)
    }

    fun checkifMobileExists(){
        authListener?.onMobileExistsCheckStarted("Check mobile...")
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(ds  in dataSnapshot.children) {
                    val user = ds.getValue(User::class.java)
                    if (user != null && user.mobile.equals(mobile)) {
                        authListener?.onMobileExists()
                        return
                    }
                }
                register()
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addListenerForSingleValueEvent(userListener)
    }

    fun register(){
        val gender_string: String = if (male!!)  "male" else "female"
        val token = KeyValueDb.get(context, Config.TOKEN, "")
        userRepository.register(first_name + " " + last_name, password!!, gender_string,
            blood_group!!, mobile!!, token)
        authListener?.onRegisterSuccess("Registration Success")
    }
}