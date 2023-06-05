package com.example.house_analysis.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R

class CompassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        supportActionBar?.hide()
    }
}