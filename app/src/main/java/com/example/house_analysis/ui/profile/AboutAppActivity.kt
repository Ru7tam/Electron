package com.example.house_analysis.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {
    lateinit var binding:ActivityAboutAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

}