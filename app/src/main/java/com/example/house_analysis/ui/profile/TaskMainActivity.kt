package com.example.house_analysis.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityTaskMainBinding

class TaskMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}