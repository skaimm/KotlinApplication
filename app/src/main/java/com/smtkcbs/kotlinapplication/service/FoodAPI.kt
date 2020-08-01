package com.smtkcbs.kotlinapplication.service

import com.smtkcbs.kotlinapplication.model.Food
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface FoodAPI {


    // https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")

    // we will use rx java for request instead of retrofit
    // Call<List<Food>> for retrofit call

    // to call just once so use single<> in rxjava
    fun getFoods() : Single<List<Food>>
}