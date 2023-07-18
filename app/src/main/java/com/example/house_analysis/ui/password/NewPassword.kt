package com.example.house_analysis.ui.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.databinding.ActivityNewPasswordBinding

class NewPassword : AppCompatActivity() {
    private lateinit var binding:ActivityNewPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        forBackButton()
        supportActionBar?.hide()
    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

}