package com.example.house_analysis.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivitySignUpBinding
import com.example.house_analysis.ui.SignInActivity
import com.example.house_analysis.ui.additional.SuccessfullyRegisteredActivity
import com.google.android.material.textfield.TextInputLayout
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
    private lateinit var genderText: TextInputLayout

    private lateinit var phone: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var confirmPassword: TextInputLayout

    private lateinit var iHave18: CheckBox
    private lateinit var policy: CheckBox

    private var isFieldsValid = false
    private var isMailValid = false
    private val textInputLayouts = mutableListOf<TextInputLayout>()

    private lateinit var signUpBtn: Button
    private var arePasswordsEqual: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.autoCompleteText.setAdapter(adapter)
        supportActionBar?.hide()
        onBackBtnPressed()

        datePicker()
        initFields()
        passwordsValidation()
        errorOccuredInEmail()
        fortextSignIn()
        forButtonSignUp()

        validationFields(
            name,
            lastname,
            gender,
            dateBirth,
            phone,
            email,
            password,
            confirmPassword,
            iHave18,
            policy
        )

    }

    fun onBackBtnPressed() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun forButtonSignUp() {
        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this, SuccessfullyRegisteredActivity::class.java)
            startActivity(intent)
        }
    }

    fun fortextSignIn() {
        binding.textSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun datePicker() {

        val defaultDate = Calendar.getInstance()
        selectedYear = defaultDate.get(Calendar.YEAR)
        selectedMonth = defaultDate.get(Calendar.MONTH)

        selectedDayOfMonth = defaultDate.get(Calendar.DAY_OF_MONTH)

        binding.dateBirth.setOnClickListener {

            val datePicker = DatePickerDialog(
                this,
                R.style.dialogTheme,
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
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.main_color))
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.grey))
            datePicker.setCancelable(false)

        }


    }

    fun passwordsValidation() {
        var password1 = binding.password
        var password2 = binding.confirmPassword

        val LATIN_STRING: String = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
        password1.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.isNotEmpty()) {
                password1.helperText = "Пароль валидный"
            } else {
                binding.password.error = resources.getString(R.string.errorOccured)
            }
            if (text.toString() == password2.editText?.text.toString()) {
                arePasswordsEqual = true
                password1.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
            } else {
                password1.startIconDrawable = null
                password2.startIconDrawable = null
                arePasswordsEqual = false
                password2.helperText = null

            }

        }
        password2.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString() == password1.editText?.text.toString()) {
                arePasswordsEqual = true
                password2.helperText = "Пароли совпадают"
                password1.helperText = null
                password1.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.startIconDrawable =
                    resources.getDrawable(R.drawable.baseline_check_circle_24)
                password2.error = null
            } else {
                arePasswordsEqual = false
                password2.error = "Пароли не совпадают"
                password1.startIconDrawable = null
                password2.startIconDrawable = null
            }

        }
    }

    fun initFields() {
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
            when (field) {
                is TextInputLayout -> {
                    textInputLayouts.add(field)
                    field.editText?.doOnTextChanged { text, start, before, count -> updateSignUpButtonState() }
//
                }

                is CheckBox -> {
                    field.setOnCheckedChangeListener { _, _ ->
                        updateSignUpButtonState()
                    }
                }

                is AutoCompleteTextView -> {
                    field.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            updateSignUpButtonState()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
//                            updateSignUpButtonState()
                        }

                    }
                    field.doOnTextChanged { text, start, before, count -> updateSignUpButtonState() }
                }

                is TextView -> {
                    field.doOnTextChanged { text, start, before, count ->
                        updateSignUpButtonState()
                    }
                }

            }
        }
        updateSignUpButtonState()
    }

    private fun updateSignUpButtonState() {
        isFieldsValid = areAllFieldsValid()

        binding.buttonSignUp.isEnabled = isFieldsValid
        signUpBtn.setOnClickListener {
            if (isFieldsValid) {
                startActivity(Intent(this, SuccessfullyRegisteredActivity::class.java))
            } else null
        }
    }

    private fun areAllFieldsValid(): Boolean {
        val isTextInputLayoutsValid = textInputLayouts.all {
            it.editText?.text?.isNotEmpty() == true
        }
        val isCheckBoxesValid = iHave18.isChecked && policy.isChecked
        val isAutoCompleteTextViewValid = gender.text?.isNotEmpty() == true
        val isTextViewValid = dateBirth.text?.isNotEmpty() == true

        return isMailValid && arePasswordsEqual && isTextInputLayoutsValid && isCheckBoxesValid && isAutoCompleteTextViewValid && isTextViewValid
    }

    fun errorOccuredInEmail() {
        email.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (inputText != null && inputText.isNotEmpty()) {
                if ("@" !in inputText) {
                    email.error = "Неверный формат почты"
                    isMailValid = false
                } else if ("@" in inputText) {
                    email.isErrorEnabled = false
                    isMailValid = true
                }
            } else {
                email.isErrorEnabled = false
            }
        }
    }

}


