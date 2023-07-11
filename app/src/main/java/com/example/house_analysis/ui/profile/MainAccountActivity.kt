package com.example.house_analysis.ui.profile

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.house_analysis.R
import com.example.house_analysis.databinding.ActivityAccountMainBinding
import com.example.house_analysis.ui.profile.bottom_nav.AddFragment
import com.example.house_analysis.ui.profile.bottom_nav.DotsFragment
import com.example.house_analysis.ui.profile.bottom_nav.TasksFragment
import com.example.house_analysis.ui.profile.bottom_nav.SearchFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.house_analysis.ui.profile.top_nav.ProfileFragment
import com.example.house_analysis.ui.profile.top_nav.SettingsFragment

class MainAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        forClickSettingImageView()
        forClickLabelImageView()
        forClickProfileImageView()
//        forDefaultFragment()

        openDefaultFragment(savedInstanceState)
        bottomNavigation()

    }

    fun forClickSettingImageView(){
        binding.settingsImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.relativeContainer, fragment)
                .commit()
        }
    }

    fun forClickLabelImageView(){
        binding.labelImageView.setOnClickListener {
            // Изменение цвета ImageView
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
            binding.settingsImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)
            binding.profileImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = TasksFragment() //TODO("Заменить на Label фрагмент")
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
            binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorImageView), PorterDuff.Mode.SRC_IN)

            // Переход на другой фрагмент
            val fragment = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.relativeContainer, fragment)
                .commit()
        }
    }



    // функция для того, чтобы фрагмент Tasks открывался по дефолту
    // By Yaroslav (Lump) Это не Tasks фрагмент. Tasks фрагмент это HomeFragment
    fun forDefaultFragment(){   
        binding.labelImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorChangeImage), PorterDuff.Mode.SRC_IN)
        val frameLayout: RelativeLayout = findViewById(R.id.relativeContainer)

        val fragment: Fragment = TasksFragment() // Создаем экземпляр TasksFragment

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(frameLayout.id, fragment)
        fragmentTransaction.commit()
    }

    fun bottomNavigation(){
        binding.navBar.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.home ->
                        openFragment(TasksFragment())
                    R.id.search ->
                        openFragment(SearchFragment())
                    R.id.add ->
                        openFragment(AddFragment())
                    R.id.dots ->
                        openFragment(DotsFragment())
                    else -> false
                }
            }
        }

//    Navigating each Fragment
    fun openFragment(fragment: Fragment): Boolean{
        supportFragmentManager.beginTransaction()
            .replace(R.id.relativeContainer, fragment)
            .commit()
        return true
    }
    fun openDefaultFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            val defaultFrament = TasksFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.relativeContainer, defaultFrament)
                .commit()
        }
    }
    fun bottomSheetDialog():Boolean{
//        val bottomSheetDialog = findViewById<LinearLayout>(R.id.bottomSheetDialog)
//        bottomSheetDialog.setOnClickListener {

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottomsheet)
        val closeBtn = dialog.findViewById<Button>(R.id.cancel)
        closeBtn?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        dialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT // Set height to wrap content
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        return true
//        }
    }





}