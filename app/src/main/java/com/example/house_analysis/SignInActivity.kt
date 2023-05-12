package com.example.house_analysis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.databinding.ActivityMainBinding
import com.example.house_analysis.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()

        var editTextMail = binding.editTextMail
        errorOccuredInEmail(editTextMail)

    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun errorOccuredInEmail(editTextMail : TextInputLayout){
        editTextMail.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (inputText != null) {
                if ("@" !in inputText){
                    editTextMail.error = "Неверный формат почты"
                }
                else if ("@" in inputText){
                    editTextMail.isErrorEnabled = false
                }
            }
            else {
                editTextMail.isErrorEnabled = false
            }


        }
    }

}