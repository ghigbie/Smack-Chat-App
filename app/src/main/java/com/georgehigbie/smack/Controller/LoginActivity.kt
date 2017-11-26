package com.georgehigbie.smack.Controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.georgehigbie.smack.R
import com.georgehigbie.smack.Services.AuthService
import com.georgehigbie.smack.Services.ToastService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableSpinner(false)
    }

    fun loginLoginButtonClicked(view: View){
        enableSpinner(true)
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()

        AuthService.loginUser(this, email, password) { loginSuccess ->
            if (loginSuccess) {
                AuthService.findUserByEmail(this) { findSuccess ->
                    if (findSuccess) {
                        enableSpinner(false)
                        finish()
                    }else{
                        ToastService.createToastLong(this, "The user entered could not be found. Please try again.")
                        enableSpinner(false)
                    }
                }
            }else{
                ToastService.createToastLong(this, "There was an login error. Please try again.")
                enableSpinner(false)
            }
        }
    }

    fun loginCreateUserButtonClicked(view: View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }

    fun enableSpinner(enable: Boolean){
        if(enable){
            loginSpinner.visibility = View.VISIBLE
        }else{
            loginSpinner.visibility = View.INVISIBLE
        }
        loginLoginButton.isEnabled = !enable
        loginCreateUserButton.isEnabled = !enable
    }
}
