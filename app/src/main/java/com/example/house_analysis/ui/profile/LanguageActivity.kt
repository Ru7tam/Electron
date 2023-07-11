package com.example.house_analysis.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.house_analysis.LanguageChangedActivity
import com.example.house_analysis.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {
    lateinit var binding: ActivityLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
        changeLanguage()
    }

    fun forBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun changeLanguage() {
        with(binding) {
            englishLanguageLinear.setOnClickListener {
                val currentLanguage = LanguageChangedActivity.getCurrentLanguage(this@LanguageActivity)
                val newLanguage = if (currentLanguage == "en") "ru" else "en"

                val intent = Intent(this@LanguageActivity, LanguageChangedActivity::class.java)
                intent.putExtra("language", newLanguage)
                startActivity(intent)
            }

        }
    }
}