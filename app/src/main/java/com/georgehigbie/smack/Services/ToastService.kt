package com.georgehigbie.smack.Services

import android.content.Context
import android.widget.Toast

/**
 * Created by georgehigbie on 11/25/17.
 */
object ToastService {

    fun createToastShort(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun createToastLong(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}