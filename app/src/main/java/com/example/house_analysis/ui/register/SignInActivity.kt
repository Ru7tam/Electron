package com.example.house_analysis.ui.register

import android.content.Intent
import android.os.Bundle

import androidx.core.widget.doOnTextChanged
import androidx.appcompat.app.AppCompatActivity
import com.example.house_analysis.databinding.ActivitySignInBinding
import com.example.house_analysis.ui.password.RestorePass
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forBackButton()
        forForgotPassword()

        var editTextMail = binding.editTextMail
        errorOccuredInEmail(editTextMail)
        fortextSignIn()

    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun forForgotPassword(){
        binding.forgotPassword.setOnClickListener{
            val intent = Intent(this, RestorePass::class.java)
            startActivity(intent)
        }
    }


    fun errorOccuredInEmail(editTextMail : TextInputLayout){
        editTextMail.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (inputText != null && inputText.isNotEmpty()) {
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

    fun fortextSignIn(){
        binding.textSignIn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}