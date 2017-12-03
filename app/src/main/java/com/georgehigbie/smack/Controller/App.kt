package com.georgehigbie.smack.Controller

import android.app.Application
import com.georgehigbie.smack.Utilities.SharedPrefs

/**
 * Created by georgehigbie on 12/2/17.
 */
class App: Application(){

    companion object {
        lateinit var sharedPreferences: SharedPrefs
    }

    override fun onCreate() {
        sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()
    }

}