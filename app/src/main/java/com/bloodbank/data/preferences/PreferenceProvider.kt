package net.simplifiedcoding.mvvmsampleapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val KEY_TOKEN = "key_token"

class PreferenceProvider(
    context: Context
){
    private val appContext = context.applicationContext

    private  val preference : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setToken(savedAt: String){
        preference.edit().putString(KEY_TOKEN, savedAt).apply()
    }

    fun getToken(): String? {
        return preference.getString(KEY_TOKEN, "")
    }
}