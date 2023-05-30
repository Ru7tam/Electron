package com.example.house_analysis.ui.password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityNewPasswordBinding
import com.example.house_analysis.ui.additional.SuccessfullyRegisteredActivity
import com.google.android.material.textfield.TextInputLayout

class NewPassword : AppCompatActivity() {
    private lateinit var binding:ActivityNewPasswordBinding
    private var arePasswordsEqual: Boolean = false

    private lateinit var doneBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        doneBtn = binding.done
        forBackButton()
        supportActionBar?.hide()
        passwordsValidation()
        updateSignUpButtonState()

    }

    fun forBackButton(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun passwordsValidation(){
        var password1 = binding.password
        var password2 = binding.confirmPassword

        fun hasWhitespace(text: CharSequence?) : Boolean{
            var ifWhitespace : Boolean = text.toString().any { it.isWhitespace() }
            return ifWhitespace
        }

        password1.editText?.doOnTextChanged { text, start, before, count ->
            var hasNoRussianLetter : Boolean = !text.toString().lowercase().any{ it in 'а' .. 'я'}

            if (text?.length!! > 7 && text.isNotEmpty() && hasNoRussianLetter) {
                if (hasWhitespace(text)){
                    password1.error = "Без пробелов"
                    arePasswordsEqual = false
                }
                else {
                    password1.error = null
                    password1.helperText = "Пароль валидный"
                }

            } else {
                binding.password.error = resources.getString(R.string.errorOccured)
            }
            if (text.toString() == password2.editText?.text.toString() && text.toString().isNotEmpty()){
                arePasswordsEqual = true
                password1.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
            }
            else {
                password1.startIconDrawable = null
                password2.startIconDrawable = null
                arePasswordsEqual = false
                password2.helperText = null

            }

        }
        password2.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.toString() == password1.editText?.text.toString() && password1.editText?.text.toString().isNotEmpty()) {
                arePasswordsEqual = true
                password2.helperText = "Пароли совподают"
                password1.helperText = null
                password1.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.error = null

            } else {
                arePasswordsEqual = false
                password2.error = "Пароли не совподают"
                password1.startIconDrawable = null
                password2.startIconDrawable = null
            }

        }
    }

    private fun updateSignUpButtonState() {
        doneBtn.isEnabled = arePasswordsEqual
        d("MyLog", "${arePasswordsEqual}")
        doneBtn.setOnClickListener{ if (arePasswordsEqual) { startActivity(Intent(this, SuccessfullyRegisteredActivity::class.java)) } else null}
    }
}