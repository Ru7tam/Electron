package com.example.house_analysis.ui.password

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.house_analysis.databinding.ActivityRestorePassBinding

class RestorePass : AppCompatActivity() {
    private lateinit var binding: ActivityRestorePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
        forButtonGetCode()
    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun forButtonGetCode(){
        binding.buttonGetCode.setOnClickListener {
            val intent = Intent(this, RestorePassSecond::class.java)
            startActivity(intent)
        }
    }

}