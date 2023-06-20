package com.example.house_analysis.ui.register

import API_ROUTE
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log.d
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.example.house_analysis.R
import com.example.house_analysis.backend.api.authentication.api_auth
import com.example.house_analysis.backend.api.authentication.auth_register_user
import com.example.house_analysis.ui.register.SignInActivity
import com.example.house_analysis.databinding.ActivitySignUpBinding
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
    private lateinit var genderText : TextInputLayout

    private lateinit var phone: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var confirmPassword: TextInputLayout

    private lateinit  var iHave18: CheckBox
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
        errorOccuredInEmail()
        fortextSignIn()

        validationFields(name, lastname, gender, dateBirth, phone, email, password, confirmPassword, iHave18, policy)

        auth_register_user_fun("2023-06-20", "alim@gmail.com", "Ismail ajiodjpo asds", "MALE", "A12345678", "9293490289")
    }

    fun onBackBtnPressed(){
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun fortextSignIn(){
        binding.textSignIn.setOnClickListener{
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
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.main_color))
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.grey))
            datePicker.setCancelable(false)

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
        signUpBtn.setOnClickListener{ if (isFieldsValid) { startActivity(Intent(this, SuccessfullyRegisteredActivity::class.java)) } else null}
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

    fun errorOccuredInEmail(){
        email.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
            if (inputText != null && inputText.isNotEmpty()) {
                if ("@" !in inputText){
                    email.error = "Неверный формат почты"
                    isMailValid = false
                }
                else if ("@" in inputText){
                    email.isErrorEnabled = false
                    isMailValid = true
                }
            }
            else {
                email.isErrorEnabled = false
            }
        }
    }

        fun auth_register_user_fun(birthday: String,
                               email: String,
                               fullname: String,
                               gender: String,
                               password: String,
                               phone: String){
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(API_ROUTE.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val apiService = retrofit.create(api_auth::class.java)

            val requestData = auth_register_user(birthday, email, fullname, gender, password, phone)
            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(requestData))

            val call = apiService.register_user(requestBody)

            call.enqueue(object : Callback<auth_register_user> {
                override fun onResponse(call: Call<auth_register_user>, response: Response<auth_register_user>) {
                    d("mytag", "Res: $response")
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        // Process the responseData here
                        d("mytag", "$responseData")
                    } else {
                        // Handle unsuccessful response
                        d("mytag", "${response.body()}")
                    }
                }

                override fun onFailure(call: Call<auth_register_user>, t: Throwable) {
                    // Handle network or other errors
                    d("mytag", "ON FAILURE: $t")
                }
            })


    }

}


