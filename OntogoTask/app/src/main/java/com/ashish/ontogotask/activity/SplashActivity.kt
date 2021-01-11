package com.ashish.ontogotask.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.ashish.ontogotask.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val timer = object : CountDownTimer(3000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                loadActivity()
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

            }

        }
        timer.start()
    }

    private fun loadActivity() {
startActivity(Intent(this,LoginActivity::class.java))
    }


}