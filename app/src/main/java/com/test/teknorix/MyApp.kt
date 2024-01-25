package com.test.teknorix

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.test.teknorix.repository.Repository
import com.test.teknorix.repository.api.ApiHelper
import com.test.teknorix.repository.api.ApiService

class MyApp : Application() {

    lateinit var repository: Repository

    private var sharedPref: SharedPreferences? = null

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        initialize()
    }

    fun getEditor(context: Context): SharedPreferences.Editor? {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences("Load Data", MODE_PRIVATE)
        }
        return sharedPref?.edit()
    }

    fun getSharedPref(context: Context): SharedPreferences? {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences("Load Data", MODE_PRIVATE)
        }
        return sharedPref
    }

    private fun initialize() {
        val apiService = ApiHelper.getInstance().create(ApiService::class.java)
        repository = Repository(apiService)
    }
}