package com.smtkcbs.kotlinapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smtkcbs.kotlinapplication.model.Food
import com.smtkcbs.kotlinapplication.service.FoodDB
import kotlinx.coroutines.launch

class FoodDetailsViewModel(application: Application) : BaseViewModel(application) {

    val foodData = MutableLiveData<Food>()

    fun getDataFromRoom(foodID : Int){

        launch {
            val dao = FoodDB(getApplication()).foodDao()
            val food = dao.getFood(foodID)
            foodData.value = food
        }
    }
}