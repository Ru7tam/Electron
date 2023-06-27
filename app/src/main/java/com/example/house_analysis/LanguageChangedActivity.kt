package com.example.house_analysis

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.preference.PreferenceManager
import java.util.Locale

class LanguageChangedActivity: Application() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val language = getCurrentLanguage(this)
        updateLanguage(language)
    }

    open fun updateLanguage(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Перезагрузить все активности
        restartActivities()
    }

    private fun restartActivities() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        private const val PREF_LANGUAGE = "PREF_LANGUAGE"

        fun setCurrentLanguage(context: Context, language: String) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(PREF_LANGUAGE, language)
            editor.apply()
        }

        fun getCurrentLanguage(context: Context): String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(PREF_LANGUAGE, "ru") ?: "ru"
        }
    }

}