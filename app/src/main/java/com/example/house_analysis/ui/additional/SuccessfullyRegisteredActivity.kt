package com.example.house_analysis.ui.additional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R

class SuccessfullyRegisteredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfully_registered)
        supportActionBar?.hide()
    }
}