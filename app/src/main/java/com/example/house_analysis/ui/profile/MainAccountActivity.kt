package com.example.house_analysis.ui.profile

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityAccountMainBinding

class MainAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forClickSettingImageView()
        forClickTasksImageView()
        forClickProfileImageView()
        forDefaultFragment()

    }

    fun forClickSettingImageView(){
        binding.settingsImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.tasksImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.relativeContainer, fragment)
                .commit()
        }
    }

    fun forClickTasksImageView(){
        binding.tasksImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.tasksImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = TasksFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.relativeContainer, fragment)
                .commit()
        }
    }

    fun forClickProfileImageView(){
        binding.profileImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.tasksImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.relativeContainer, fragment)
                .commit()
        }
    }

    // функция для того, чтобы фрагмент Tasks открывался по дефолту
    fun forDefaultFragment(){   
        binding.tasksImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
        val frameLayout: RelativeLayout = findViewById(R.id.relativeContainer)

        val fragment: Fragment = TasksFragment() // Создаем экземпляр TasksFragment

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(frameLayout.id, fragment)
        fragmentTransaction.commit()
    }


}