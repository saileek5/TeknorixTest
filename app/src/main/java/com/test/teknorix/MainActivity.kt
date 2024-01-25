package com.test.teknorix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.teknorix.databinding.ActivityMainBinding
import com.test.teknorix.repository.preferences.PreferenceManager
import com.test.teknorix.repository.preferences.PrefsHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferenceHelper: PrefsHelper by lazy { PreferenceManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetching App load count
        val loadCount = preferenceHelper.getAppLoadCount()
        val newCountValue = loadCount.plus(1)
        // Saving the incremented value
        preferenceHelper.setAppLoadCount(newCountValue)
    }
}