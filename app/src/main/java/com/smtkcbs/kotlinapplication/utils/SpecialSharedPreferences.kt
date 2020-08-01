package com.smtkcbs.kotlinapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPreferences {

    private val TIMEKEY = "time"

    companion object {


        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var  instance : SpecialSharedPreferences?= null
        private var lock = Any()

        operator fun invoke(context: Context) : SpecialSharedPreferences = instance ?: synchronized(lock){
            instance ?: createSSP(context).also {
                instance = it
            }
        }

        private fun createSSP(context: Context) : SpecialSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
                return SpecialSharedPreferences()
        }
    }

    fun saveTheTime( time : Long){
        sharedPreferences?.edit( commit = true){
            putLong(TIMEKEY,time)
        }
    }

    fun getTıme() = sharedPreferences?.getLong(TIMEKEY,0)
}