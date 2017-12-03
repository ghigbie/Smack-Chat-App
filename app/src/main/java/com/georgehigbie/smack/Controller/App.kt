package com.georgehigbie.smack.Controller

import android.app.Application
import com.georgehigbie.smack.Utilities.SharedPrefs

/**
 * Created by georgehigbie on 12/2/17.
 */
class App: Application(){

    companion object {
        lateinit var prefs SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }

}