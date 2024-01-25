package com.test.teknorix.repository.api

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ApiHelper {

    companion object {
        private const val BASE_URL = "https://reqres.in/"

        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

        private val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val request = chain.request().newBuilder()
                    .method(original.method, original.body)
                    .build()
                try {
                    chain.proceed(request)
                } catch (e: Exception) {
                    e.printStackTrace()
                    throw e
                } catch (e: Throwable) {
                    if (e is IOException) {
                        throw e
                    } else {
                        throw IOException(e)
                    }
                }
            })
            .addInterceptor(interceptor)
            .build()

        fun getInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}