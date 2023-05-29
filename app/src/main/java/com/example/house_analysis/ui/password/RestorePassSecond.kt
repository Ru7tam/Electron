package com.example.house_analysis.ui.password

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.house_analysis.databinding.ActivityRestorePassSecondBinding

class RestorePassSecond : AppCompatActivity() {
    private lateinit var binding: ActivityRestorePassSecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePassSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
        buttonEnterCode()
    }

    fun forBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun buttonEnterCode() {
        binding.buttonEnterCode.setOnClickListener {
            val intent = Intent(this, NewPassword::class.java)
            startActivity(intent)
        }
    }

}