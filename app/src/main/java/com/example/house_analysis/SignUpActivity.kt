package com.example.house_analysis

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isNotEmpty
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.databinding.ActivityMainBinding
import com.example.house_analysis.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDayOfMonth = 0

//    All Inputs:
    private lateinit var name: TextInputLayout
    private lateinit var lastname: TextInputLayout
    private lateinit var dateBirth: TextView

    private lateinit var gender: AutoCompleteTextView

    private lateinit var phone: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var confirmPassword: TextInputLayout

    private lateinit  var iHave18: CheckBox
    private lateinit var policy: CheckBox

    private var isFieldsValid = false

    private lateinit var signUpBtn: Button

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
        initFields()

        validationFields(name, lastname, dateBirth, phone, email, password, confirmPassword, iHave18, policy)

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
    fun initFields(){
        binding.apply {
            this@SignUpActivity.name = name
            this@SignUpActivity.lastname = lastname
            this@SignUpActivity.dateBirth = dateText
            this@SignUpActivity.gender = autoCompleteText
            this@SignUpActivity.phone = phone
            this@SignUpActivity.email = email
            this@SignUpActivity.password = password
            this@SignUpActivity.confirmPassword = confirmPassword
            this@SignUpActivity.iHave18 = iHave18checkbox
            this@SignUpActivity.policy = checkboxPolicy
            this@SignUpActivity.signUpBtn = buttonSignUp
        }
    }

    fun validationFields(vararg fields: View) {
        fields.forEach { field ->
            when(field) {
                is TextInputLayout -> {
                    field.editText
                        ?.validator()
                        ?.nonEmpty()
                        ?.addErrorCallback { updateSignUpButtonState() }
                }
                is CheckBox -> {
                    field.setOnCheckedChangeListener { _, _ -> updateSignUpButtonState() }
                }
                is AutoCompleteTextView-> {
                    field.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            updateSignUpButtonState()
                        }
                        override fun afterTextChanged(s: Editable?) {}
                    })
                }
                is TextView -> {
                    field.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            updateSignUpButtonState()
                        }
                        override fun afterTextChanged(s: Editable?) {}
                    })
                }
            }
            updateSignUpButtonState()
        }
    }
    private fun updateSignUpButtonState() {
        val isFieldsValid = areAllFieldsValid()

        signUpBtn.isEnabled = isFieldsValid
        signUpBtn.setBackgroundColor(resources.getColor(if (isFieldsValid) R.color.main_color else R.color.grey))
        signUpBtn.setOnClickListener{ if (isFieldsValid) { startActivity(Intent(this, SignInActivity::class.java)) } else null}
    }

    private fun areAllFieldsValid(vararg textInputs: TextInputLayout): Boolean {
        val textInputLayouts = arrayOf(name, lastname, phone, email, password, confirmPassword)

        val isTextInputLayoutsValid = textInputLayouts.all { it.editText?.text?.isNotEmpty() == true }
        val isCheckBoxesValid = iHave18.isChecked && policy.isChecked
        val isAutoCompleteTextViewValid = gender.text?.isNotEmpty() == true
        val isTextViewValid = dateBirth.text?.isNotEmpty() == true

        return isTextInputLayoutsValid && isCheckBoxesValid && isAutoCompleteTextViewValid && isTextViewValid
    }


}


