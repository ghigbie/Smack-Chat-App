package com.georgehigbie.smack

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View){

    }

    fun generateColorClicked(view: View){
        val random = Random()
        val redValue = random.nextInt(255)
        val greenValue = random.nextInt(255)
        val blueValue = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue)) //this sets the background color of the image

        val savedRedValue = redValue.toDouble() / 255
        val savedGreenValue = greenValue.toDouble() / 255
        val savedBlueValue = blueValue.toDouble() /255

        avatarColor = "[$savedRedValue, $savedGreenValue, $savedBlueValue]"
    }

    fun createUserClicked(view: View){

    }
}
