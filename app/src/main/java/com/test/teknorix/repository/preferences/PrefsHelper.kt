package com.test.teknorix.repository.preferences

interface PrefsHelper {
    fun setAppLoadCount(userId: Int)
    fun getAppLoadCount(): Int
}