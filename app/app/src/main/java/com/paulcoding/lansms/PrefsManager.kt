package com.paulcoding.lansms

import android.content.Context

class PrefsManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getIP(): String = sharedPreferences.getString(IP_KEY, "") ?: ""
    fun setIP(ip: String) = sharedPreferences.edit().putString(IP_KEY, ip).apply()
}