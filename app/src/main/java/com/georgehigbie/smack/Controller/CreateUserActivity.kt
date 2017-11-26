package com.georgehigbie.smack.Controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.georgehigbie.smack.R
import com.georgehigbie.smack.Services.AuthService
import com.georgehigbie.smack.Services.ToastService
import com.georgehigbie.smack.Services.UserDataService
import com.georgehigbie.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        enableSpinner(false)
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
        val redValue = random.nextInt(255)
        val greenValue = random.nextInt(255)
        val blueValue = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue)) //this sets the background color of the image

        val savedRedValue = redValue.toDouble() / 255
        val savedGreenValue = greenValue.toDouble() / 255
        val savedBlueValue = blueValue.toDouble() /255

        avatarColor = "[$savedRedValue, $savedGreenValue, $savedBlueValue, 1]"
    }

    fun createUserClicked(view: View) {
        enableSpinner(true)
        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    println("REGISTER COMPLETE")
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            println("LOGIN COMPLETE")
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    //developer notifications
                                    println(UserDataService.avatarName)
                                    println(UserDataService.avatarColor)
                                    println(UserDataService.name)
                                    println("CREATE COMPLETE")
                                    println("DONE!!!!!")

                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    ToastService.createToastLong(this,"Something went wrong with user creation. Please try again.")
                                }
                            }
                        } else {
                            ToastService.createToastLong(this,"Something went wrong with the login process. Please try again")
                        }
                    }
                } else {
                    ToastService.createToastLong(this,"Something went wrong with the registration process. Please try again.")
                }
            }
        }else{
            ToastService.createToastLong(this,"Make sure user name, email, and password are completed.")
            enableSpinner(false)
        }
    }

    fun errorToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if (enable){
            createSpinner.visibility = View.VISIBLE
        }else{
            createSpinner.visibility = View.INVISIBLE
        }
        createUserButton.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorButton.isEnabled = !enable
    }
}
