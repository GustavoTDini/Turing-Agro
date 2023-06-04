package com.example.turing_agro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class GeneratingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generating)
        Handler(Looper.getMainLooper()).postDelayed({
            val plannerIntent = Intent(this, PlannerActivity::class.java)
            startActivity(plannerIntent)
        }, 5000)
    }
}