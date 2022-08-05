package com.elthobhy.nasatechport.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.elthobhy.nasatechport.R
import com.elthobhy.nasatechport.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToMain()
    }

    private fun goToMain() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finishAffinity()
            },2000)
    }
}