package com.georgehigbie.smack.Services

import android.graphics.Color
import com.georgehigbie.smack.Controller.App
import java.util.*

/**
 * Created by georgehigbie on 11/22/17.
 */
object UserDataService {

    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logout(){
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
    }

    fun retrunAvatarColor(components: String): Int{

        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")

        var redValue = 0
        var greenValue = 0
        var blueValue = 0

        val scanner = Scanner(strippedColor)
        if(scanner.hasNext()){
            redValue = (scanner.nextDouble() * 255).toInt()
            greenValue = (scanner.nextDouble() * 255).toInt()
            blueValue = (scanner.nextDouble()* 255).toInt()
        }

        return Color.rgb(redValue, greenValue, blueValue)
    }

}