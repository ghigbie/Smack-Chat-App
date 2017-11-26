package com.georgehigbie.smack.Controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.georgehigbie.smack.R
import com.georgehigbie.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginButtonClicked(view: View){
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            AuthService.loginUser(this, email, password){ loginSucess ->
                if(loginSucess){
                    AuthService.findUserByEmail(this){ findSuccess ->
                        if(findSuccess){
                            finish()
                        }
                    }
                }
            }
        }
    }

    fun loginCreateUserButtonClicked(view: View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }
}
