package com.example.house_analysis.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R

class AboutAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        supportActionBar?.hide()
    }
}