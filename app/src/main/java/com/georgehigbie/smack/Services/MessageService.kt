package com.georgehigbie.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.georgehigbie.smack.Model.Channel
import com.georgehigbie.smack.Utilities.URL_GET_CHANNELS

/**
 * Created by georgehigbie on 11/28/17.
 */
object MessageService {
    val channels = ArrayList<Channel>()

    fun getChannels(context: Context, complete: (Boolean) -> Unit){

        val channelsRequest = object: JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener {

        }, Response.ErrorListener { error ->
            Log.d("Error", "Could not retrieve channels")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${AuthService.authToken}")
                return headers
            }
        }
    }
}