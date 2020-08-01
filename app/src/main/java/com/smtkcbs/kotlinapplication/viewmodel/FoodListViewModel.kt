package com.smtkcbs.kotlinapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.smtkcbs.kotlinapplication.model.Food
import com.smtkcbs.kotlinapplication.service.FoodAPIService
import com.smtkcbs.kotlinapplication.service.FoodDB
import com.smtkcbs.kotlinapplication.utils.SpecialSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {

    private val updateTime = 10*(60*1000*1000*1000L)
    private val foodApiService = FoodAPIService()
    // advantages of using rxjava is using disposable
    private val disposable = CompositeDisposable()
    private val specialSharedPreference = SpecialSharedPreferences(getApplication())


    val foods = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refreshData(){

        val savedTime  = specialSharedPreference.getTıme()
        if(savedTime != null && savedTime !=0L && System.nanoTime() - savedTime < updateTime){
            // get data from sqlite
            getDataFromSQLite()
        }else{
            // get data from internet
            getDataFromInternet()
        }
    }

    fun refresh(){
        getDataFromInternet()
    }
    private fun getDataFromSQLite(){
        loading.value = true

        launch {
            val foodList = FoodDB(getApplication()).foodDao().getAllFoods()
            showFoods(foodList)
            Toast.makeText(getApplication(),"Data come From Room", Toast.LENGTH_LONG).show()
        }
    }
    private fun getDataFromInternet(){

        loading.value = true

        disposable.add(

            foodApiService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        saveDataToSQLite(t)
                        Toast.makeText(getApplication(),"Data come From Internet ", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {

                        errorMessage.value = true
                        loading.value = false
                    }

                })

        )
    }

    private fun showFoods( foodList : List<Food>){
        foods.value = foodList
        errorMessage.value = false
        loading.value = false
    }

    private fun saveDataToSQLite(foodList: List<Food>){
        launch {

            val dao = FoodDB(getApplication()).foodDao()
            dao.deleteAllFoods()
            val ids = dao.insertAll(*foodList.toTypedArray())
            var i =0
            while (i<foodList.size){
                foodList.get(i).foodID = ids.get(i).toInt()
                i++
            }
            showFoods(foodList)
        }

        specialSharedPreference.saveTheTime(System.nanoTime())
    }
}