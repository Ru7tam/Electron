package com.example.house_analysis

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isNotEmpty
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.databinding.ActivityMainBinding
import com.example.house_analysis.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDayOfMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.autoCompleteText.setAdapter(adapter)
        supportActionBar?.hide()
        datePicker()
        passwordsValidation()
        checkAllInputs()
    }

    fun datePicker() {

        val defaultDate = Calendar.getInstance()
        selectedYear = defaultDate.get(Calendar.YEAR)
        selectedMonth = defaultDate.get(Calendar.MONTH)

        selectedDayOfMonth = defaultDate.get(Calendar.DAY_OF_MONTH)

        binding.dateBirth.setOnClickListener {

            val datePicker = DatePickerDialog(
                this,
                0,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    selectedYear = year
                    selectedMonth = month
                    selectedDayOfMonth = dayOfMonth


                    binding.dateText.text = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                    binding.dateText.setTextColor(resources.getColor(R.color.black))
                },
                selectedYear,
                selectedMonth,
                selectedDayOfMonth
            )

            datePicker.show()
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "Choose"
        }
        passwordsValidation()

    }

    fun passwordsValidation() {
        val LATIN_STRING: String = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
        binding.password.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.isNotEmpty()) {
                binding.password.helperText = "Пароль валидный"
            } else {
                binding.password.isHelperTextEnabled = false
            }
        }
        binding.confirmPassword.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.isNotEmpty()) {
                binding.confirmPassword.helperText = "Пароль валидный"
                d("MyLog", "${binding.confirmPassword.editText!!.text}")
                if (binding.confirmPassword.editText!!.text.toString() == binding.password.editText?.text.toString()) {
                    binding.confirmPassword.helperText = "Пароли совподают"
                    binding.password.isHelperTextEnabled = false
                    binding.confirmPassword.startIconDrawable =
                        resources.getDrawable(R.drawable.baseline_check_circle_24)
                    binding.password.startIconDrawable =
                        resources.getDrawable(R.drawable.baseline_check_circle_24)
                } else {
                    binding.confirmPassword.helperText = resources.getString(R.string.errorOccured)
                    binding.confirmPassword.isStartIconVisible = false
                    binding.password.isStartIconVisible = false
                }
            } else {
                binding.confirmPassword.helperText = resources.getString(R.string.errorOccured)
                binding.confirmPassword.isStartIconVisible = false
                binding.password.isStartIconVisible = false
            }
        }

    }

    fun checkAllInputs() {
//        d("MyLog", binding.nameText.text.toString())
//        if (binding.nameText.text?.isEmpty() == true) d("MyLog", "${binding.nameText.text?.isNotEmpty()}")
        if (binding.nameText.text?.length!! > 0
//            binding.lastNameText.text?.isEmpty() == true &&
//            binding.dateText.text.isNotEmpty() &&
//            binding.autoCompleteText.text.isNotEmpty() &&
//            binding.phoneText.text?.isNotEmpty() == true &&
//            binding.emailText.text?.isNotEmpty() == true &&
//            binding.passwordText.text?.isNotEmpty() == true &&
//            binding.confirmPasswordText.text?.isNotEmpty() == true &&
//            binding.iHave18checkbox.isChecked &&
//            binding.checkboxPolicy.isChecked
        ) {
            val intent = Intent(applicationContext, SignInActivity::class.java).apply {
                binding.buttonSignIn.setBackgroundColor(resources.getColor(R.color.main_color))
                startActivity(intent)
            }
        }
    }
}


