package com.nailnafir.jajanin

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.nailnafir.jajanin.network.HttpClient

class Jajanin : MultiDexApplication() {
    companion object {
        lateinit var instance: Jajanin

        fun getApp(): Jajanin {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    // penyimpanan lokal
    fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    // mengatur data token
    fun setToken(token: String) {
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
        HttpClient.getInstance().buildRetrofitClient(token)
    }

    // mengambil data token
    fun getToken(): String? {
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    // mengatur data user
    fun setUser(user: String) {
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
        HttpClient.getInstance().buildRetrofitClient(user)
    }

    // mengambil data user
    fun getUser(): String? {
        return getPreferences().getString("PREFERENCES_USER", null)
    }
}