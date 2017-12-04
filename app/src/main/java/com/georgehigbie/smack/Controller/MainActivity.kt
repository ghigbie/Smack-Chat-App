package com.georgehigbie.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import com.georgehigbie.smack.Model.Channel
import com.georgehigbie.smack.R
import com.georgehigbie.smack.Services.AuthService
import com.georgehigbie.smack.Services.MessageService
import com.georgehigbie.smack.Services.ToastService
import com.georgehigbie.smack.Services.UserDataService
import com.georgehigbie.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import com.georgehigbie.smack.Utilities.SOCKET_URL
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    val socket = IO.socket(SOCKET_URL)
    lateinit var channelAdapter: ArrayAdapter<Channel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        socket.connect()
        socket.on("channelCreated", onNewChannel)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setUpAdapters()

        if(App.prefs.isLoggedIn){
            AuthService.findUserByEmail(this){}
        }
    }

    override fun onResume() {
        //the local broadcast manager should be in onResume
        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReciever,
                IntentFilter(BROADCAST_USER_DATA_CHANGE))
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userDataChangeReciever)
        socket.disconnect()
    }

    private fun setUpAdapters(){
        channelAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,  MessageService.channels)
        channel_list.adapter = channelAdapter
    }


    private val userDataChangeReciever = object: BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent?) {
            if(App.prefs.isLoggedIn){
                userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                val resourceId = resources.getIdentifier(UserDataService.avatarName, "drawable", packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.retrunAvatarColor(UserDataService.avatarColor))
                loginButtonNavHeader.text = "Logout"

                loadChannelsList()
            }
        }
    }

    fun loadChannelsList(){
        MessageService.getChannels({ complete ->
            if(complete){
                channelAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginButtonNavClicked(view: View){
        if(App.prefs.isLoggedIn){
            UserDataService.logout()
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            loginButtonNavHeader.text = "Login"
        }else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    fun addChannelClicked(view: View){
        if(App.prefs.isLoggedIn){
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null)
            builder.setView(dialogView)
                    .setPositiveButton(R.string.add){ dialogInterface, i ->
                        val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNameText)
                        val descriptionTextField = dialogView.findViewById<EditText>(R.id.addChannelDescriptionText)

                        val channelName = nameTextField.text.toString()
                        val channelDescription = descriptionTextField.text.toString()

                        //create channel with the channel name and description
                        socket.emit("newChannel", channelName, channelDescription)

                    }
                    .setNegativeButton(R.string.cancel){ dialogInterface, i ->
                        //cancel and close the dialog when clicked

                    }
                    .show()
        }
    }

    private val onNewChannel = Emitter.Listener { args ->
        runOnUiThread {
            val channelName = args[0] as String
            val channelDescription = args[1] as String
            val channelId = args[2] as String

            val newChannel = Channel(channelName, channelDescription, channelId)
            MessageService.channels.add(newChannel)
            println(newChannel.name)
            println(newChannel.description)
            println(newChannel.id)
            channelAdapter.notifyDataSetChanged()
        }
    }

    fun sendMessageButtonClicked(view: View){
        ToastService.toasMakerTest(this)
        hideKeyboard()
    }

   fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

}
