package com.example.submission2.main

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    private val PREFC_NAME = "share_preference"
    private val sharePreference : SharedPreferences
    val edit: SharedPreferences.Editor

    init{
        sharePreference = context.getSharedPreferences(PREFC_NAME, Context.MODE_PRIVATE)
        edit = sharePreference.edit()
    }

    fun put(key: String, value: Boolean){
        edit.putBoolean(key, value)
            .apply()
    }
    fun getBoolean (key: String): Boolean{
        return sharePreference.getBoolean(key,false)
    }
}