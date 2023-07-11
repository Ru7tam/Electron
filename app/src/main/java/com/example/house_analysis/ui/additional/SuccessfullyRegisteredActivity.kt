package com.example.house_analysis.ui.additional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivitySuccessfullyRegisteredBinding
import com.example.house_analysis.ui.register.SignInActivity

class SuccessfullyRegisteredActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessfullyRegisteredBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfullyRegisteredBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        onOkBtnPressed()
    }

    private fun onOkBtnPressed(){
        binding.btnSuccess.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}