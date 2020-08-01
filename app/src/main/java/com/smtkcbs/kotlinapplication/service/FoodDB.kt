package com.smtkcbs.kotlinapplication.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smtkcbs.kotlinapplication.model.Food

@Database(entities = arrayOf(Food::class),version = 1)
abstract class FoodDB : RoomDatabase() {

    abstract fun foodDao() : FoodDAO


    companion object {

        @Volatile private var instance : FoodDB?= null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDB(context).also {
                instance = it
            }
        }

        private fun createDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDB::class.java,
            "fooddb").build()

    }
}