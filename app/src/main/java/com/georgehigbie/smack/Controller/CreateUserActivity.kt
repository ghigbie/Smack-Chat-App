package com.georgehigbie.smack.Controller

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.georgehigbie.smack.R
import com.georgehigbie.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View){
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if(color == 0){
            userAvatar = "light$avatar"
        }else{
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View){
        val random = Random()
        val redValue = random.nextInt(256)
        val greenValue = random.nextInt(256)
        val blueValue = random.nextInt(256)

        createAvatarImageView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue)) //this sets the background color of the image

        val savedRedValue = redValue.toDouble() / 256
        val savedGreenValue = greenValue.toDouble() / 256
        val savedBlueValue = blueValue.toDouble() /256

        avatarColor = "[$savedRedValue, $savedGreenValue, $savedBlueValue, 1]"
    }

    fun createUserClicked(view: View){
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()
        AuthService.registerUser(this, email, password){ registerSuccess ->
            if(registerSuccess){
                AuthService.loginUser(this, email, password){ loginSuccess ->
                    if(loginSuccess){

                    }

                }
            }
        }
    }
}
