package com.test.teknorix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
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

//        val repository = (application as MyApp).repository

//        var dataValue = MyApp().getSharedPref(applicationContext)?.getInt("data", 0)
        var loadCount = preferenceHelper.getAppLoadCount()
        Log.d("data shared pref", loadCount.toString())
        var newCountValue = loadCount?.plus(1)
        if (newCountValue != null) {
//            MyApp().getEditor(applicationContext)?.putInt("data", newValue)?.apply()
            preferenceHelper.setAppLoadCount(newCountValue)
        }
    }
}