package com.test.teknorix

import android.app.Application
import android.content.Context
import com.test.teknorix.repository.Repository
import com.test.teknorix.repository.api.ApiHelper
import com.test.teknorix.repository.api.ApiService

class MyApp : Application() {

    lateinit var repository: Repository

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        initialize()
    }

    private fun initialize() {
        val apiService = ApiHelper.getInstance().create(ApiService::class.java)

        //Initializing repository
        repository = Repository(apiService)
    }
}