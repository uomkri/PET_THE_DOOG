package com.dich.dich2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import com.dich.dich2.screens.splash.SplashFragmentDirections
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VK.login(this, arrayListOf(VKScope.WALL, VKScope.STATUS, VKScope.PHOTOS))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("actiity", "result")
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Repo.setSuccess()
                Log.e("token", token.accessToken)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(applicationContext, "login failed: error code $errorCode", Toast.LENGTH_LONG).show()
            }

        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
            Log.e("data", "null")
        }
    }
}