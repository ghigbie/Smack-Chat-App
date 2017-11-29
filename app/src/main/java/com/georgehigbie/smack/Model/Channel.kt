package com.georgehigbie.smack.Model

/**
 * Created by georgehigbie on 11/27/17.
 */
class Channel(val name: String, val description: String, val id: String){

    override fun toString(): String {
        return "#$name"
    }


}