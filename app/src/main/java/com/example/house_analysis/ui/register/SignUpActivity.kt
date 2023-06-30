package com.example.house_analysis.ui.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log.d
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivitySignUpBinding
import com.example.house_analysis.network.api.RequestRepositoryProvider
import com.example.house_analysis.ui.additional.SuccessfullyRegisteredActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // For Android 9 (API level 28) and higher
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build()
            StrictMode.setThreadPolicy(policy)
        }


        val items = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.autoCompleteText.setAdapter(adapter)
        supportActionBar?.hide()
        onBackBtnPressed()

        datePicker()
        initFields()
        passwordsValidation()
        errorOccurredInEmail()
        forTextSignIn()


        validationFields(name, lastname, gender, dateBirth, phone, email, password, confirmPassword, iHave18, policy)
    }

    private fun registrationRequest() {
        val fullName = name.editText?.text.toString() + " " + lastname.editText?.text.toString()
        val email = email.editText?.text.toString()
        val password = confirmPassword.editText?.text.toString()
        val gender = if(gender.text.toString() == "Мужской") "MALE" else "FEMALE" //TODO (HARDCODED)
        val birthday = formatDate(dateBirth.text)
        val phone = phone.editText?.text.toString()

        networkRepository.registerUser(fullName, email, password, gender, birthday.toString(), phone)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d("Network", result.toString())
                    when (result.code()) {
                        200 -> {
                            startActivity(Intent(this, SuccessfullyRegisteredActivity::class.java))
                            finish()
                        }
                        else -> applicationContext.toast("Что-то пошло не так, повторите попытку через несколько минут")
                    }
                }, { error ->
                    Exception(error).printStackTrace()
                }
            )
    }

    private fun formatDate(date: CharSequence): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        return LocalDate.parse(date, formatter).toString()
    }

    private fun Context.toast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onBackBtnPressed(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun forTextSignIn(){
        binding.textSignIn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun datePicker() {

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
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.main_color))
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.grey))
            datePicker.setCancelable(false)

        }


    }

    private fun passwordsValidation(){
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
                    arePasswordsEqual = true
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
    private fun initFields(){
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

    private fun validationFields(vararg fields: View) {
        fields.forEach { field ->
            when(field) {
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
                is AutoCompleteTextView-> {
                    field.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
                    field.doOnTextChanged { text, start, before, count ->  updateSignUpButtonState() }
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

        signUpBtn.setOnClickListener{
            if (isFieldsValid) {
                registrationRequest()
            } else applicationContext.toast("Проверьте поля и исправьте ошибки")
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

    private fun errorOccurredInEmail(){
        email.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (!inputText.isNullOrEmpty()) {
                if ("@" !in inputText){
                    email.error = "Неверный формат почты"
                    isMailValid = false
                }
                else {
                    email.isErrorEnabled = false
                    isMailValid = true
                }
            }
            else {
                email.isErrorEnabled = false
            }
        }
    }


}


