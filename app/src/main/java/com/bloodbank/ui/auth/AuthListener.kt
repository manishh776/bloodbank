package com.bloodbank.ui.auth

interface AuthListener{
    fun onInputError(message : String)
    fun onMobileExistsCheckStarted(message: String)
    fun onMobileExists()
    fun onRegisterSuccess(message: String)
    fun onLoginSuccess()
    fun onLoginFailed()
    fun onLoginStarted()
    fun onFailure(message: String)
}