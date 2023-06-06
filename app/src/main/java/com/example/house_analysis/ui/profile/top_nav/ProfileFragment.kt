package com.example.house_analysis.ui.profile.top_nav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.house_analysis.databinding.FragmentProfileBinding
import com.example.house_analysis.ui.profile.AboutAppActivity
import com.example.house_analysis.ui.profile.CompassActivity
import com.example.house_analysis.ui.profile.LanguageActivity

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        forLanguage()
        forCompass()
        forAboutApp()
        return binding.root
    }

    fun forLanguage(){
        binding.languageLinearLayout.setOnClickListener {
            val intent = Intent(requireContext(),LanguageActivity::class.java)
            startActivity(intent)
        }
    }

    fun forCompass(){
        binding.compassLinearLayout.setOnClickListener {
            val intent = Intent(requireContext(),CompassActivity::class.java)
            startActivity(intent)
        }
    }

    fun forAboutApp(){
        binding.aboutAppLinearLayout.setOnClickListener {
            val intent = Intent(requireContext(),AboutAppActivity::class.java)
            startActivity(intent)
        }
    }

}
